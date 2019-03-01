package utils;

import java.awt.*;
import java.awt.event.InputEvent;

public class Mouse {

    public static void LeftClick(int x, int y,int button) throws Exception {
        Robot click = new Robot();
        click.mouseMove(x, y);
//        click.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//        click.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        click.mousePress(button);
        click.mouseRelease(button);
    }

    public static void RightClick(int x, int y, int button) throws Exception {
        Robot click = new Robot();
        click.mouseMove(x, y);
        click.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        click.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        click.mousePress(button);
        click.mouseRelease(button);
    }
}
