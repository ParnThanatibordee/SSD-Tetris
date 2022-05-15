import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame {
    private Board board;
    private int boardSizeX = 10;
    private int boardSizeY = 15;
    private GridUi gridUi;
    private Thread thread;
    private long delayed = 200;
    private boolean gameOver;
    public Game() {
        board = new Board(boardSizeX, boardSizeY);
        gameOver = false;
        gridUi = new GridUi();
        add(gridUi);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void start() {
        setVisible(true);

        thread = new Thread() {
            @Override
            public void run() {
                while(!gameOver) {
                    // update
                    // game logic
                    waitFor(delayed);
                }
            }
        };
        thread.start();
    }

    private void waitFor(long delayed) {
        try {
            Thread.sleep(delayed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class GridUi extends JPanel {
        public static final int CELL_PIXEL_SIZE = 30;

        public GridUi() {
            setPreferredSize(new Dimension(boardSizeX * CELL_PIXEL_SIZE,
                    boardSizeY * CELL_PIXEL_SIZE));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 0; i < boardSizeX; i++) {
                for (int j = 0; j < boardSizeY; j++) {
                    paintCell(g, i, j);
                    }
                }
            }

        private void paintCell(Graphics g, int row, int col) {
            int x = col * CELL_PIXEL_SIZE;
            int y = row * CELL_PIXEL_SIZE;

            Cell cell = board.getCell(row, col);

            if (cell.isCovered()) {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x + 1, y + 1,
                        CELL_PIXEL_SIZE - 2, CELL_PIXEL_SIZE - 2);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
