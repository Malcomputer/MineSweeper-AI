package java_game;

import javax.swing.*;
import java.awt.*;

public class Cool extends JFrame {

    private Cool() {
        JLabel statusbar = new JLabel();
        add(statusbar, BorderLayout.SOUTH);

        String path = "src/resources/40x40/smile.png";
        Image icon = new ImageIcon(path).getImage();

        add(new Board(statusbar, 40));
        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Cool().setVisible(true));
    }

}