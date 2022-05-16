import java.util.ArrayList;

public class Board {
    private int boardSizeX;
    private int boardSizeY;
    private Cell [][] cells;  // cells[col][row]
    private ArrayList<Block> blocks = new ArrayList<Block>();

    public Board(int boardSizeX, int boardSizeY) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
    }

    public boolean blockOverCeil() {
        Integer maxY = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if (maxY < blocks.get(i).getY())
                maxY = blocks.get(i).getY();
        }

        return maxY >= boardSizeY - 1;
    }

    public void removeFullFillRow() {
        for (int col = 0; col < boardSizeY; col++) {
            if (blockFullFillRow(col)) {
                // remove block in that row
            }
        }
    }

    public boolean blockFullFillRow(int col) {
        boolean filled = true;
        for (int row = 0; row < boardSizeX; row++) {
            if (!cells[col][row].isBlocked()) {
                filled = false;
            }
        }
        return filled;
    }

    public void updateBoard() {
        removeFullFillRow();
        blockFall();
        // re-position of blocks
    }

    public void blockFall() {

    }

}
