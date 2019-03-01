package utils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

public class Ride {

    private boolean running = false;

    public static void main(String args[]) throws AWTException, InterruptedException {
        new Ride();
    }


    public Ride() throws AWTException, InterruptedException {
        fun();
    }

    private void fun() throws InterruptedException, AWTException {
//        KeyListener
//        if (KeyEvent.KEY_PRESSED ){
//
//        }
        if (running) {
            running = false;
            goal();
        } else {
            running = true;
        }
    }

    private void goal() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        int count = 1;
        while (count <= 10) {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            TimeUnit.MILLISECONDS.sleep(200);
            count++;
        }
    }
}