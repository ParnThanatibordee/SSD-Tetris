import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends JFrame {
    private int boardSizeX = 10;
    private int boardSizeY = 20;
    private GridUi gridUi;
    public static final int CELL_PIXEL_SIZE = 30;
    private Thread thread;
    private long delayed = 200;
    private boolean gameOver;

    private Board board;

    public Game() {
        Board board = new Board();

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
                    gridUi.repaint();
                    // game logic
                    gameOver = isGameOver();

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

        public GridUi() {
            setPreferredSize(new Dimension(boardSizeX * CELL_PIXEL_SIZE,
                    boardSizeY * CELL_PIXEL_SIZE));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }

    private boolean isGameOver() {
        // เอาตามวิธีที่เก็บ cell
        ArrayList<Cell> cells = board.getCells();
        Integer maxY = 0;
        for (int i = 0; i < cells.size(); i++) {
            if (maxY < cells.get(i).getY())
                maxY = cells.get(i).getY();
        }

        return maxY >= boardSizeY - 1;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
