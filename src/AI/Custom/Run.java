package AI.Custom;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class Run {
    public static int wins;
    public static int loses;
    private static Random random = new Random();
    private static MyOwn game;

    public static void main(String[] args) throws AWTException, InterruptedException {
        Thread.sleep(2000);
        game = new MyOwn(1000);
        System.out.println("Columns: " + game.numColumns);
        System.out.println("Rows: " + game.numRows);
        System.out.println("SmileyState: " + game.smileyState);
        for (int i = 1; ; i++) {
            System.out.println("Game " + i);
            if (solveGame()) {
                wins++;
            } else {
                loses++;
            }
        }
    }

    private static boolean solveGame() {
        game.clickSmiley();
        game.rereadCells();
        while (true) {
            int x, y;
            x = random.nextInt(game.numColumns);
            y = random.nextInt(game.numRows);
            game.clickCell(x, y, InputEvent.BUTTON1_DOWN_MASK);
            game.rereadSmiley();
            if (game.smileyState == 1) { // Frown
                System.out.println("Lost game");
                return false;
            } else if (game.smileyState == 2) {  // Sunglasses
                System.out.println("Won game");
                return true;
            }
            game.rereadCells();
            while (true) {
                boolean changed = false;
                while (solveSingles() || solvePairs())
                    changed = true;
                if (!changed) {
                    break;
                }
                game.rereadSmiley();
                if (game.smileyState == 2) {  // Sunglasses
                    System.out.println("Won game");
                    return true;
                } else if (game.smileyState == 1)
                    throw new RuntimeException("Cannot lose with a safe strategy");
                game.rereadSmiley();
            }
        }
    }

    private static boolean solveSingles() {
        boolean changed = false;
        return false;
    }

    private static boolean solvePairs() {
        return false;
    }


//            game.rereadCells();
//            while (true) {
//                boolean changed = false;
//                while (solvePairs()) {
//                    changed = true;
//                }
//                if (!changed) {
//                    break;
//                }
//                game.rereadSmiley();
//                if (game.smileyState == 2) {  // Sunglasses
//                    return true;
//                }
//                if (game.smileyState == 1) {
//                    throw new RuntimeException("Cannot lose with safe strategy");
//                }
//                game.rereadCells();
//            }
//        }
//    }

//    private static boolean solvePairs() {
//        boolean changed = false;
//        for (int y = 0; y < game.numRows; y++) {
//            for (int x = 0; x < game.numColumns; x++) {
//                int state = game.getCell(x, y);
//                if (state < 1 || state > 8) {
//                    continue;
//                }
//                state -= countNeighboring(x, y, 10);
//                for (int ny = y - 1; ny <= y + 1; ny++) {
//                    fail:
//                    for (int nx = x - 1; nx <= x + 1; nx++) {
//                        if (nx == x && ny == y || !game.isInBounds(nx, ny)) {
//                            continue;
//                        }
//                        int neighstate = game.getCell(nx, ny);
//                        if (neighstate < 1 || neighstate > 8) {
//                            continue;
//                        }
//                        neighstate -= countNeighboring(nx, ny, 10);
//                        for (int yy = y - 1; yy <= y + 1; yy++) {
//                            for (int xx = x - 1; xx <= x + 1; xx++) {
//                                if (game.getCell(xx, yy) == 9 && !isNeighbor(xx, yy, nx, ny)) {
//                                    continue fail;
//                                }
//                            }
//                        }
//                        if (neighstate == state) {
//                            for (int yy = ny - 1; yy <= ny + 1; yy++) {
//                                for (int xx = nx - 1; xx <= nx + 1; xx++) {
//                                    if (game.getCell(xx, yy) == 9 && !isNeighbor(xx, yy, x, y)) {
//                                        game.clickCell(xx, yy, InputEvent.BUTTON1_DOWN_MASK);
//                                        game.setCell(xx, yy, 12);
//                                        changed = true;
//                                    }
//                                }
//                            }
//                        } else if (neighstate - state == countNeighboring(nx, ny, 9) - countNeighboring(x, y, 9)) {
//                            for (int yy = ny - 1; yy <= ny + 1; yy++) {
//                                for (int xx = nx - 1; xx <= nx + 1; xx++) {
//                                    if (game.getCell(xx, yy) == 9 && !isNeighbor(xx, yy, x, y)) {
//                                        game.clickCell(xx, yy, InputEvent.BUTTON3_DOWN_MASK);
//                                        game.setCell(xx, yy, 10);
//                                        changed = true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return changed;
//    }

//    private static int countNeighboring(int x, int y, int value) {
//        int count = 0;
//        for (int yy = y - 1; yy <= y + 1; yy++) {
//            for (int xx = x - 1; xx <= x + 1; xx++) {
//                if (game.getCell(xx, yy) == value)
//                    count++;
//            }
//        }
//        return count;
//    }

//    private static boolean isNeighbor(int x0, int y0, int x1, int y1) {
//        return Math.max(Math.abs(x0 - x1), Math.abs(y0 - y1)) <= 1;
//    }
}
