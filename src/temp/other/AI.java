package temp.other;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

import static java.awt.Window.getWindows;

public class AI extends JFrame {

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        Rectangle size = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        robot.createScreenCapture(size);


//        AI m = new AI();
//        Main m = new Main();


//
//        List<WindowInfo> list = m.getWindows();
//
//        for (int i=0;i<list.size();i++)
//        {
//            WindowInfo info = list.get(i);
//            System.out.println(info.getTitle());
//        }


//        Rectangle rect = null;
//        for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
//            if (desktopWindow.getTitle().contains("Minesweeper")) {
//                rect.setRect(desktopWindow.getLocAndSize());
//            }

//        Rectangle rect = null;
//        for (DesktopWindow desktopWindow : getAllWindowNames()) {
//            if (desktopWindow.getTitle().contains("IDEA")) {
//                rect = desktopWindow.getLocAndSize();
//            }
//        }


//        Mouse.LeftClick(400, 175);
//        Win  ("Minesweeper");
//        Window[] hi = Window.getWindows();
//        System.out.println(Arrays.toString(hi));
    }
}
