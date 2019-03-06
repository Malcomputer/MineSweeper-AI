package my_version;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import static utils.Cell.*;

public class MinePanel extends JPanel {

    private final int NUM_IMAGES = 13;
    private final int NUM_MINES = 7;
    private final int panel_size;
    private int ROWS_COLS = 16;
    private int allCells;
    private int cell_size;
    private int minesLeft;
    private int[] field;
    private Image[] img;
    private boolean inGame;
    private final JLabel statusbar;

    public MinePanel(JLabel label, int panel_size) {
        this.panel_size = panel_size;
        this.statusbar = label;
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(panel_size, panel_size));
        cell_size = panel_size / ROWS_COLS;
        img = new Image[NUM_IMAGES];
        for (int i = 0; i < NUM_IMAGES; i++) {
//            String path = "src/resources/15x15/" + i + ".png";
//            String path = "src/resources/20x20/" + i + ".png";
            String path = "src/resources/40x40/" + i + ".png";
//            String path = "src/resources/200x200/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
        addMouseListener(new ClickAdapter());
        newGame();
    }

    private void newGame() {

        int cell;

        Random random = new Random();
        inGame = true;
        minesLeft = NUM_MINES;

        allCells = ROWS_COLS * ROWS_COLS;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {
            field[i] = HIDDEN;
        }

        statusbar.setText(Integer.toString(minesLeft));

        int i = 0;

        while (i < NUM_MINES) {

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != HIDDEN_MINE_CELL)) {

                int current_col = position % ROWS_COLS;
                field[position] = HIDDEN_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - ROWS_COLS;
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

                    cell = position + ROWS_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - ROWS_COLS;
                if (cell >= 0) {
                    if (field[cell] != HIDDEN_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + ROWS_COLS;
                if (cell < allCells) {
                    if (field[cell] != HIDDEN_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (ROWS_COLS - 1)) {
                    cell = position - ROWS_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != HIDDEN_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + ROWS_COLS + 1;
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
        int current_col = j % ROWS_COLS;
        int cell;
        if (current_col > 0) {
            cell = j - ROWS_COLS - 1;
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
            cell = j + ROWS_COLS - 1;
            if (cell < allCells) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
        cell = j - ROWS_COLS;
        if (cell >= 0) {
            if (field[cell] > MINE) {
                field[cell] -= HIDDEN;
                if (field[cell] == BLANK) {
                    find_empty_cells(cell);
                }
            }
        }
        cell = j + ROWS_COLS;
        if (cell < allCells) {
            if (field[cell] > MINE) {
                field[cell] -= HIDDEN;
                if (field[cell] == BLANK) {
                    find_empty_cells(cell);
                }
            }
        }
        if (current_col < (ROWS_COLS - 1)) {
            cell = j - ROWS_COLS + 1;
            if (cell >= 0) {
                if (field[cell] > MINE) {
                    field[cell] -= HIDDEN;
                    if (field[cell] == BLANK) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + ROWS_COLS + 1;
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
        for (int i = 0; i < ROWS_COLS; i++) {
            for (int j = 0; j < ROWS_COLS; j++) {
                int cell = field[(i * ROWS_COLS) + j];
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
                g.drawImage(img[cell], (j * cell_size), (i * cell_size), this);
            }
        }
        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame) {
            statusbar.setText("Game lost");
        }
    }

    private class ClickAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int cCol = x / cell_size;
            int cRow = y / cell_size;
            boolean doRepaint = false;
            if (!inGame) {
                newGame();
                repaint();
            }
            if ((x < ROWS_COLS * cell_size) && (y < ROWS_COLS * cell_size)) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field[(cRow * ROWS_COLS) + cCol] > MINE) {
                        doRepaint = true;
                        if (field[(cRow * ROWS_COLS) + cCol] <= HIDDEN_MINE_CELL) {
                            if (minesLeft > 0) {
                                field[(cRow * ROWS_COLS) + cCol] += FLAGGED;
                                minesLeft--;
                                statusbar.setText(Integer.toString(minesLeft));
                            } else {
                                statusbar.setText("No marks left");
                            }
                        } else {
                            field[(cRow * ROWS_COLS) + cCol] -= FLAGGED;
                            minesLeft++;
                            statusbar.setText(Integer.toString(minesLeft));
                        }
                    }
                } else {
                    if (field[(cRow * ROWS_COLS) + cCol] > HIDDEN_MINE_CELL) {
                        return;
                    }
                    if ((field[(cRow * ROWS_COLS) + cCol] > MINE) && (field[(cRow * ROWS_COLS) + cCol] < MARKED_MINE_CELL)) {
                        field[(cRow * ROWS_COLS) + cCol] -= HIDDEN;
                        doRepaint = true;
                        if (field[(cRow * ROWS_COLS) + cCol] == MINE) {
                            inGame = false;
                        }
                        if (field[(cRow * ROWS_COLS) + cCol] == BLANK) {
                            find_empty_cells((cRow * ROWS_COLS) + cCol);
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