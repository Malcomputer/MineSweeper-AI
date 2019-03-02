package AI.solver;

import AI.solver.utils.Pair;
import AI.solver.utils.Tile;

import java.util.HashMap;
import java.util.Map;

public class SolverBoard {
    public int boardWidth;
    public int boardHeight;
    public double boardPix;
    public int boardTopW;
    public int boardTopH;

    private HashMap<Pair, Tile> tiles;

    public SolverBoard(int boardWidth, int boardHeight, double boardPix, int boardTopW, int boardTopH) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.boardPix = boardPix;
        this.boardTopW = boardTopW;
        this.boardTopH = boardTopH;
//        tiles = new HashMap<Pair, Tile>(boardWidth * boardHeight);
    }
//
//    public int getTile(int x, int y) {
//        Tile tile;
//        for (Map.Entry entry : tiles.entrySet()) {
//            tile = (Tile) entry.getValue();
//            if (tile.getX() == x && tile.getY() == y) return tile.getValue();
//        }
//        return -1;
//    }
//
//    public void logBoard() {
//        //todo
//    }
}