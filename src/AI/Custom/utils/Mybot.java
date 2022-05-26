package AI.Custom.utils;

import temp.TempRun;

import java.awt.*;

public class Mybot {
    private Robot robot;
    private int delay;  // Milliseconds
    private Point lastMouseLocation;

    public Mybot(int delay) throws AWTException {
        robot = new Robot();
        robot.setAutoWaitForIdle(true);
        lastMouseLocation = null;
        this.delay = delay;
    }

    public GameIcons getScreenshot(Rectangle rect) {
        return new GameIcons(robot.createScreenCapture(rect));
    }

    public void click(int x, int y, int button) {
        if (lastMouseLocation != null && !MouseInfo.getPointerInfo().getLocation().equals(lastMouseLocation)) {
            System.err.println("Mouse moved. Program aborted\n" + TempRun.wins + " Wins - " + TempRun.loses + " loses");
            System.exit(1);
            return;
        }
        robot.mouseMove(x, y);
        lastMouseLocation = new Point(x, y);
        robot.mousePress(button);
        robot.mouseRelease(button);
        robot.delay(delay);
    }
}
