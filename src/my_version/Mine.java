package my_version;

import javax.swing.*;
import java.awt.*;

public class Mine extends JFrame {
    public static void main(String args[]) {
        new Mine().setVisible(true);
    }

    public Mine() {
        JLabel label = new JLabel();
        add(label, BorderLayout.SOUTH);
        add(new MinePanel(label, 640));
        setResizable(true);
        pack();
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}