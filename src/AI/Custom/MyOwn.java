package AI.Custom;

import AI.Custom.utils.GameIcons;
import AI.Custom.utils.Mybot;

import java.awt.*;
import java.awt.event.InputEvent;

import static AI.Custom.utils.Images.SMILEY_IMAGES;
import static AI.Custom.utils.Images.CELL_IMAGES;

class MyOwn {
    private Mybot robot;

    private final Rectangle smileyButton, gameBoard;
    private final Point cellSize;

    public int numColumns;
    public int numRows;

    public int smileyState;

    private int[][] cellStates;

    MyOwn(int actionDelay) throws AWTException {
        robot = new Mybot(actionDelay);
        Rectangle allScreensBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            for (GraphicsConfiguration gc : gd.getConfigurations())
                allScreensBounds = allScreensBounds.union(gc.getBounds());
        }
        GameIcons screenshot = robot.getScreenshot(allScreensBounds);
        Point smiley = findRegion(screenshot, SMILEY_IMAGES[0]); // Smile
        if (smiley == null)
            smiley = findRegion(screenshot, SMILEY_IMAGES[1]);  // Or try to find frown face
        if (smiley == null)
            smiley = findRegion(screenshot, SMILEY_IMAGES[2]);  // Or try to find sunglasses face
        if (smiley == null)
            throw new RuntimeException("Smile button not found on screen");
        smileyButton = new Rectangle(allScreensBounds.x + smiley.x, allScreensBounds.y + smiley.y, SMILEY_IMAGES[0].width, SMILEY_IMAGES[0].height);
        clickSmiley();
        clickSmiley();

        GameIcons UNOPENED = CELL_IMAGES[9];
        screenshot = robot.getScreenshot(allScreensBounds);

        Point topLeft = findRegion(screenshot, UNOPENED);
        if (topLeft == null)
            throw new RuntimeException("Top left unopened cell not found on screen");

        cellSize = new Point(UNOPENED.width, UNOPENED.height);
        numColumns = 0;
        for (int x = topLeft.x; x + cellSize.x <= screenshot.width && UNOPENED.equals(screenshot, x, topLeft.y); x += cellSize.x)
            numColumns++;

        // Find number of rows
        numRows = 0;
        for (int y = topLeft.y; y + cellSize.y <= screenshot.height && UNOPENED.equals(screenshot, topLeft.x, y); y += cellSize.y)
            numRows++;

        gameBoard = new Rectangle(allScreensBounds.x + topLeft.x, allScreensBounds.y + topLeft.y, numColumns * cellSize.x, numRows * cellSize.y);
        cellStates = new int[numRows][numColumns];
    }

    boolean isInBounds(int x, int y) {
        return x >= 0 && x < numColumns && y >= 0 && y < numRows;
    }

    int getCell(int x, int y) {
        if (isInBounds(x, y))
            return cellStates[y][x];
        else
            return 13;
    }

    void setCell(int x, int y, int button) {
        if (!isInBounds(x, y))
            throw new IllegalArgumentException();
        robot.click(gameBoard.x + x * cellSize.x + cellSize.x / 2, gameBoard.y + y * cellSize.y + cellSize.y / 2, button);
    }

    void clickSmiley() {
        robot.click(smileyButton.x + smileyButton.width / 2, smileyButton.y + smileyButton.height / 2, InputEvent.BUTTON1_DOWN_MASK);
    }

    void clickCell(int x, int y, int button) {
        if (!isInBounds(x, y))
            throw new IllegalArgumentException();
        robot.click(gameBoard.x + x * cellSize.x + cellSize.x / 2, gameBoard.y + y * cellSize.y + cellSize.y / 2, button);
    }

    void rereadSmiley() {
        for (int delay = 1; delay < 1000; delay *= 2) {
            smileyState = findMatchingImageIndex(robot.getScreenshot(smileyButton), 0, 0, SMILEY_IMAGES);
            if (smileyState != -1)
                return;

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
        throw new RuntimeException("Unknown smiley state");
    }

    void rereadCells() {
        for (int delay = 1; delay < 1000; delay *= 2) {
            GameIcons image = robot.getScreenshot(gameBoard);
            boolean fail = false;
            middle:
            for (int y = 0; y < numRows; y++) {
                for (int x = 0; x < numColumns; x++) {
                    cellStates[y][x] = findMatchingImageIndex(image, x * cellSize.x, y * cellSize.y, CELL_IMAGES);
                    if (cellStates[y][x] == -1) {
                        fail = true;
                        break middle;
                    }
                }
            }
            if (!fail)
                return;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Unknown cell state");
    }

    private static Point findRegion(GameIcons screen, GameIcons pattern) {
        for (int y = 0; y + pattern.height <= screen.height; y++) {
            for (int x = 0; x + pattern.width <= screen.width; x++) {
                if (pattern.equals(screen, x, y))
                    return new Point(x, y);
            }
        }
        return null;
    }

    private static int findMatchingImageIndex(GameIcons image, int offX, int offY, GameIcons[] patterns) {
        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].equals(image, offX, offY))
                return i;
        }
        return -1;
    }
}
