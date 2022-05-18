import java.util.ArrayList;

// create tertis board
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
        Integer minY = height;
        for (int i = 0; i < blocks.size(); i++) {
            if (minY > blocks.get(i).getY())
                minY = blocks.get(i).getY();
        }

        return minY < 0;
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
        blockFall();
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void blockFall() {
        for (Block block : blocks) {
            if (!block.isStopFall()) {
                if (block.getY() < height - block.getHeight()) {
                    block.movedown();
                }
                if (collisionToBottom(block) || isHit(block)) {
                    block.setStopFall(true);
                    BlockShape shape = block.getShape();
                    for (int i = 0; i < shape.getHeight(); i++) {
                        for (int j = 0; j < shape.getWidth(); j++) {
                            if (shape.isBlock(i, j)) {
                                cells[block.getX() + j][block.getY() + i].setCovered(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean collisionToBottom(Block block) {
        int blockY = block.getY();

        int blockHeight = block.getHeight();

        for (int i = 0; i < blockHeight; i++) {
            if (blockY + i >= height - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean collisionToTop(Block block) {
        int blockY = block.getY();

        int blockHeight = block.getHeight();

        for (int i = 0; i < blockHeight; i++) {
            if (blockY + i < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean collisionToRight(Block block) {
        int blockX = block.getX();
        int blockWidth = block.getWidth();
        for (int i = 0; i < blockWidth; i++) {
            if (blockX + i >= width - 1) {
                return true;
            }
        }
        return false;
    }


    public boolean collisiontoLeft(Block block) {
        int blockX = block.getX();
        int blockWidth = block.getWidth();
        for (int i = 0; i < blockWidth; i++) {
            if (blockX + i <= 0) {
                return true;
            }
        }
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public boolean isHit(Block block) {
        BlockShape shape = block.getShape();
        for (int i = 0; i < block.getHeight(); i++) {
            for (int j = 0; j < block.getWidth(); j++) {
                if(shape.isBlock(i,j)){
                    if(cells[block.getX()+j][block.getY()+i+1].isCovered() || cells[block.getX()+j][block.getY()+i].isCovered()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Cell[][] getCells() {
        return cells;
    }

}
