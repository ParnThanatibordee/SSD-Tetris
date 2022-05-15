import java.awt.*;

// create tertis board
public class Board {
    private Cell[][] cells;
    private int width;
    private int height;

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

    public Cell getCell (int row, int col) {
        if (row < 0 || col < 0 || row >= width || col >= height) { return null; }
        return cells[row][col];
    }
}