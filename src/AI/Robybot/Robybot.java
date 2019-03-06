package AI.Robybot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class Robybot {
    private static Random random = new Random();

    private static Bot game;

    public static void main(String[] args) throws AWTException, InterruptedException {
        Thread.sleep(2000);
        game = new Bot(1000);

        // Play until win
        for (int i = 1; ; i++) {
            System.out.println("Game " + i);
            if (solveGame())
                break;
        }
        System.out.println("Win!");
    }

    private static boolean solveGame() {

        game.clickSmiley();
        game.rereadCells();

        while (true) {
            // Choose a random unopened cell to click
            int x, y;
            do {
                x = random.nextInt(game.numColumns);
                y = random.nextInt(game.numRows);
            } while (game.getCell(x, y) != 9);

            game.clickCell(x, y, InputEvent.BUTTON1_DOWN_MASK);  // Left click
            game.rereadSmiley();
            if (game.smileyState == 1)  // Frown
                return false;
            else if (game.smileyState == 2)  // Sunglasses
                return true;
            game.rereadCells();

            // Try to make deterministic safe moves
            while (true) {
                boolean changed = false;
                while (solveSingles() || solvePairs())  // Try to solve as much as possible without the expensive screen reread
                    changed = true;
                if (!changed)
                    break;
                game.rereadSmiley();
                if (game.smileyState == 2)  // Sunglasses
                    return true;
                if (game.smileyState == 1)
                    throw new RuntimeException("Cannot lose with safe strategy");
                game.rereadCells();
            }
        }
    }

    private static boolean solveSingles() {
        boolean changed = false;
        for (int y = 0; y < game.numRows; y++) {
            for (int x = 0; x < game.numColumns; x++) {
                int state = game.getCell(x, y);
                if (state < 1 || state > 8)
                    continue;  // Skip non-numerical cell

                int flag = countNeighboring(x, y, 10);
                int unopened = countNeighboring(x, y, 9);
                if (flag > state)
                    throw new RuntimeException("Inconsistent game board");

                if (flag == state && unopened >= 1) {
                    game.clickCell(x, y, InputEvent.BUTTON3_DOWN_MASK);  // Middle click to open neighbors
                    for (int yy = y - 1; yy <= y + 1; yy++) {
                        for (int xx = x - 1; xx <= x + 1; xx++) {
                            if (game.getCell(xx, yy) == 9)
                                game.setCell(xx, yy, 12);
                        }
                    }
                    changed = true;

                } else if (unopened >= 1 && flag + unopened == state) {
                    for (int yy = y - 1; yy <= y + 1; yy++) {
                        for (int xx = x - 1; xx <= x + 1; xx++) {
                            if (game.getCell(xx, yy) == 9) {
                                game.clickCell(xx, yy, InputEvent.BUTTON3_DOWN_MASK);  // Right click to flag the cell
                                game.setCell(xx, yy, 10);
                            }
                        }
                    }
                    changed = true;
                }
            }
        }
        return changed;
    }

    private static boolean solvePairs() {
        boolean changed = false;
        // For each cell (x, y) with a number on it
        for (int y = 0; y < game.numRows; y++) {
            for (int x = 0; x < game.numColumns; x++) {
                int state = game.getCell(x, y);
                if (state < 1 || state > 8)
                    continue;
                state -= countNeighboring(x, y, 10);

                // For each neighbor (nx, ny) with a number on it
                for (int ny = y - 1; ny <= y + 1; ny++) {
                    fail:
                    for (int nx = x - 1; nx <= x + 1; nx++) {
                        if (nx == x && ny == y || !game.isInBounds(nx, ny))
                            continue;
                        int neighstate = game.getCell(nx, ny);
                        if (neighstate < 1 || neighstate > 8)
                            continue;
                        neighstate -= countNeighboring(nx, ny, 10);

                        // Check if each unopened neighbor of (x, y) is a neighbor of (nx, ny)
                        for (int yy = y - 1; yy <= y + 1; yy++) {
                            for (int xx = x - 1; xx <= x + 1; xx++) {
                                if (game.getCell(xx, yy) == 9 && !isNeighbor(xx, yy, nx, ny))
                                    continue fail;
                            }
                        }

                        if (neighstate == state) {  // Open all cells unique to the neighbor
                            for (int yy = ny - 1; yy <= ny + 1; yy++) {
                                for (int xx = nx - 1; xx <= nx + 1; xx++) {
                                    if (game.getCell(xx, yy) == 9 && !isNeighbor(xx, yy, x, y)) {
                                        game.clickCell(xx, yy, InputEvent.BUTTON1_DOWN_MASK);
                                        game.setCell(xx, yy, 12);
                                        changed = true;
                                    }
                                }
                            }

                        } else if (neighstate - state == countNeighboring(nx, ny, 9) - countNeighboring(x, y, 9)) {  // Flag all cells unique to the neighbor
                            for (int yy = ny - 1; yy <= ny + 1; yy++) {
                                for (int xx = nx - 1; xx <= nx + 1; xx++) {
                                    if (game.getCell(xx, yy) == 9 && !isNeighbor(xx, yy, x, y)) {
                                        game.clickCell(xx, yy, InputEvent.BUTTON3_DOWN_MASK);
                                        game.setCell(xx, yy, 10);
                                        changed = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return changed;
    }

    private static int countNeighboring(int x, int y, int value) {
        int count = 0;
        for (int yy = y - 1; yy <= y + 1; yy++) {
            for (int xx = x - 1; xx <= x + 1; xx++) {
                if (game.getCell(xx, yy) == value)
                    count++;
            }
        }
        return count;
    }

    private static boolean isNeighbor(int x0, int y0, int x1, int y1) {
        return Math.max(Math.abs(x0 - x1), Math.abs(y0 - y1)) <= 1;
    }
}
