package AI.example.utils;

import java.awt.*;

public final class MyRobot {
    private java.awt.Robot robot;
    private int delay;  // Milliseconds
    private Point lastMouseLocation;

    public MyRobot(int delay) throws AWTException {
        robot = new java.awt.Robot();
        robot.setAutoWaitForIdle(true);
        lastMouseLocation = null;
        this.delay = delay;
    }

    public GameIcons getScreenshot(Rectangle rect) {
        return new GameIcons(robot.createScreenCapture(rect));
    }

    public void click(int x, int y, int button) {
        if (lastMouseLocation != null && !MouseInfo.getPointerInfo().getLocation().equals(lastMouseLocation)) {
            System.err.println("Mouse moved. Program aborted");
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