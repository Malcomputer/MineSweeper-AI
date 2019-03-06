package temp.other.utils;

import java.awt.*;

public final class MyRobot {
    private Robot robot;
    private int delay;  // Milliseconds
    private Point lastMouseLocation;

    public MyRobot(int delay) throws AWTException {
        robot = new Robot();
        robot.setAutoWaitForIdle(true);
        lastMouseLocation = null;
        this.delay = delay;
    }

    public MyImage getScreenshot(Rectangle rect) {
        return new MyImage(robot.createScreenCapture(rect));
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