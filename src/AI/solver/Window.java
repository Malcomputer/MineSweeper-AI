package AI.solver;

import javax.swing.*;

public class Window extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window().setVisible(true));
    }

    private Window() {
        initUI();
    }

    private void initUI() {
        JLabel panel = new JLabel();
        getContentPane().add(panel);

        JButton startButton = new JButton("Start");
        startButton.setBounds(20, 20, 260, 110);
        startButton.addActionListener(actionEvent -> MSolver.run());
        startButton.setToolTipText("click to start solving Minesweeper");

        JButton cancelButton = new JButton("Exit");
        cancelButton.setBounds(210, 140, 80, 30);
        cancelButton.addActionListener(actionEvent -> System.exit(0));
        cancelButton.setToolTipText("Exit");

        panel.add(startButton);
        panel.add(cancelButton);

        setTitle("Minesweeper Solver");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
