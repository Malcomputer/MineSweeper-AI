package java_game;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    private Game() {
        JLabel statusbar = new JLabel();
        add(statusbar, BorderLayout.SOUTH);
        add(new Board(statusbar, 40));
        setResizable(false);
        pack(); //```11```
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> new Cool().setVisible(true));
        new Game().setVisible(true);
    }

}