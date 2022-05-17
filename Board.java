import java.util.ArrayList;

// create tetris board
public class Board {
    private Cell[][] cells;
    private int width;
    private int height;

    private ArrayList<Block> blocks = new ArrayList<Block>();

    private BlockShape currentShape;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        initCells();
    }

    private void initCells() {
        cells = new Cell[width][height];
        for (int row = 0; row < width; row++) {
            for (int column = 0; column < height; column++) {
                cells[row][column] = new Cell();
            }
        }
    }

    public boolean blockOverCeil() {
        Integer maxY = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if (maxY < blocks.get(i).getY())
                maxY = blocks.get(i).getY();
        }

        return maxY >= height - 1;
    }

    public void removeFullFillRow() {
        for (int col = 0; col < height; col++) {
            if (blockFullFillRow(col)) {
                for (int row = 0; row < width; row++) {
                    cells[row][col].setCovered(false);
                }
                // change row to gray color
                // เก็บสีในแต่ละ cell แทน เก็บใน block
                // เก็บใน block ด้วยก็ได้
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || col < 0 || row >= width || col >= height) {
            return null;
        }
        return cells[row][col];
    }

    public boolean blockFullFillRow(int col) {
        boolean filled = true;
        for (int row = 0; row < width; row++) {
            if (!cells[row][col].isCovered()) {
                filled = false;
            }
        }
        return filled;
    }

    public void updateBoard() {
        removeFullFillRow();
        blockFall();
        // re-position of block.
    }

    public ArrayList<Block> getBlocks(){
        return blocks;
    }

    public void blockFall() {
        for (Block block : blocks) {
            if (block.getY() < height - 1) {
                block.movedown();
            }else {
                block.setY(0);
            }
        }
    }
}
