import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameListener extends Game{
    private Thread thread;
    private boolean gameOver;
    private long delayed = 200;
    private Game.GridUi gridUi;
    private Board board;
    private int boardSizeX = 10;
    private int boardSizeY = 15;

    public GameListener(String title, String gameMode) {
        super(title, gameMode);
        gameOver = false;
        board = new Board(boardSizeX, boardSizeY);
        gridUi = new Game.GridUi();
        // listener
        // board.updateCells();
        // change gameover
    }

    @Override
    public void start() {
        setVisible(true);

        thread = new Thread() {
            @Override
            public void run() {
                while(!gameOver) {
                    // update
                    gridUi.repaint();
                    waitFor(delayed);
                }
                // after finish game
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
}
