package utils;

public class Cell {
    // Needed Cells
    public static final int BLANK = 0;
    public static final int MINE = 9;
    public static final int HIDDEN = 10;
    public static final int FLAGGED = 11;
    public static final int NO_MINE = 12;

    // Doubled Cells
    public static final int HIDDEN_MINE_CELL = MINE + HIDDEN;
    public static final int MARKED_MINE_CELL = HIDDEN_MINE_CELL + FLAGGED;
}
