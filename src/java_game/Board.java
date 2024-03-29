package java_game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static utils.Cell.*;

public class Board extends JPanel {

    private final int NUM_IMAGES = 13;
    private int CELL_SIZE = 0; // Originally 20

    private final int N_MINES;
    private final int N_ROWS_COLS = 16;

    //    private final int BOARD_WIDTH_HEIGHT = (N_ROWS_COLS * CELL_SIZE + 1) * 2;
    private final int BOARD_WIDTH_HEIGHT = (N_ROWS_COLS * 20 + 1) * 2;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel statusbar;

    public Board(JLabel statusbar, int mines) {
        this.N_MINES = mines;
        this.statusbar = statusbar;
        initBoard();
    }

    private void initBoard() {

        setPreferredSize(new Dimension(BOARD_WIDTH_HEIGHT, BOARD_WIDTH_HEIGHT));
        CELL_SIZE = BOARD_WIDTH_HEIGHT / N_ROWS_COLS;

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
//            String path = "src/resources/15x15/" + i + ".png";
//            String path = "src/resources/20x20/" + i + ".png";
            String path = "src/resources/40x40/" + i + ".png";
//            String path = "src/resources/200x200/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame() {

        int cell;

        Random random = new Random();
        inGame = true;
        minesLeft = N_MINES;

        allCells = N_ROWS_COLS * N_ROWS_COLS;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {
            field[i] = HIDDEN;
        }

        statusbar.setText(Integer.toString(minesLeft));

        int i = 0;

        while (i < N_MINES) {

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != HIDDEN_MINE_CELL)) {

                int current_col = position % N_ROWS_COLS;
                field[position] = HIDDEN_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - N_ROWS_COLS;
                    if (cell >= 0) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + N_ROWS_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_ROWS_COLS;
                if (cell >= 0) {
                    if (field[cell] != HIDDEN_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_ROWS_COLS;
                if (cell < allCells) {
                    if (field[cell] != HIDDEN_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (N_ROWS_COLS - 1)) {
                    cell = position - N_ROWS_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + N_ROWS_COLS + 1;
                    if (cell < allCells) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    public void find_empty_cells(int j) {
        int current_col = j % N_ROWS_COLS;
        int cell;
        if (current_col > 0) {
            cell = j - N_ROWS_COLS - 1;
            if (cell >= 0) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + N_ROWS_COLS - 1;
            if (cell < allCells) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
        cell = j - N_ROWS_COLS;
        if (cell >= 0) {
            if (field[cell] > MINE) {
                field[cell] -= HIDDEN;
                if (field[cell] == BLANK) {
                    find_empty_cells(cell);
                }
            }
        }
        cell = j + N_ROWS_COLS;
        if (cell < allCells) {
            if (field[cell] > MINE) {
                field[cell] -= HIDDEN;
                if (field[cell] == BLANK) {
                    find_empty_cells(cell);
                }
            }
        }
        if (current_col < (N_ROWS_COLS - 1)) {
            cell = j - N_ROWS_COLS + 1;
            if (cell >= 0) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + N_ROWS_COLS + 1;
            if (cell < allCells) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int uncover = 0;
        for (int i = 0; i < N_ROWS_COLS; i++) {
            for (int j = 0; j < N_ROWS_COLS; j++) {
                int cell = field[(i * N_ROWS_COLS) + j];
                if (inGame && cell == MINE) {
                    inGame = false;
                }
                if (!inGame) {
                    if (cell == HIDDEN_MINE_CELL) {
                        cell = MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = FLAGGED;
                    } else if (cell > HIDDEN_MINE_CELL) {
                        cell = NO_MINE;
                    } else if (cell > MINE) {
                        cell = HIDDEN;
                    }
                } else {
                    if (cell > HIDDEN_MINE_CELL) {
                        cell = FLAGGED;
                    } else if (cell > MINE) {
                        cell = HIDDEN;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * CELL_SIZE), (i * CELL_SIZE), this);
            }
        }
        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame) {
            statusbar.setText("Game lost");
        }
    }

    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;
            boolean doRepaint = false;
            if (!inGame) {
                newGame();
                repaint();
            }
            if ((x < N_ROWS_COLS * CELL_SIZE) && (y < N_ROWS_COLS * CELL_SIZE)) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field[(cRow * N_ROWS_COLS) + cCol] > MINE) {
                        doRepaint = true;
                        if (field[(cRow * N_ROWS_COLS) + cCol] <= HIDDEN_MINE_CELL) {
                            if (minesLeft > 0) {
                                field[(cRow * N_ROWS_COLS) + cCol] += FLAGGED;
                                minesLeft--;
                                statusbar.setText(Integer.toString(minesLeft));
                            } else {
                                statusbar.setText("No marks left");
                            }
                        } else {
                            field[(cRow * N_ROWS_COLS) + cCol] -= FLAGGED;
                            minesLeft++;
                            statusbar.setText(Integer.toString(minesLeft));
                        }
                    }
                } else {
                    if (field[(cRow * N_ROWS_COLS) + cCol] > HIDDEN_MINE_CELL) {
                        return;
                    }
                    if ((field[(cRow * N_ROWS_COLS) + cCol] > MINE) && (field[(cRow * N_ROWS_COLS) + cCol] < MARKED_MINE_CELL)) {
                        field[(cRow * N_ROWS_COLS) + cCol] -= HIDDEN;
                        doRepaint = true;
                        if (field[(cRow * N_ROWS_COLS) + cCol] == MINE) {
                            inGame = false;
                        }
                        if (field[(cRow * N_ROWS_COLS) + cCol] == BLANK) {
                            find_empty_cells((cRow * N_ROWS_COLS) + cCol);
                        }
                    }
                }
                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}