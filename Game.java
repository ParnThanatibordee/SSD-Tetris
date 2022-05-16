import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame {
    private int boardSizeX = 10;
    private int boardSizeY = 15;
    private GridUi gridUi;
    private Thread thread;
    private long delayed = 200;
    private boolean gameOver;

    private Board board;
    private BlockFactory blockFactory;
    private Block currentControlBlock = null;

    public Game() {
        addKeyListener(new Controller());

        board = new Board(boardSizeX, boardSizeY);
        blockFactory = new BlockFactory();

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
                    board.updateBoard();
                    gridUi.repaint();
                    // game logic
                    // ถ้า currentControlBlock นิ่งแล้วให้ extract block มาใหม่
                    // แล้ว set currentControlBlock ใหม่
                    // รอเอาโค้ด Block fall

                    gameOver = isGameOver();

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
  
    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // ใน control block (block) ต้องมีเช็คด้วยว่าถูก control อยู่รึเปล่า
            // currentControlBlock อาจจะเก็บใน game หรือ block factory
            if (currentControlBlock != null) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Command c = new CommandMoveDown(currentControlBlock);
                    c.execute();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    Command c = new CommandMoveLeft(currentControlBlock);
                    c.execute();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Command c = new CommandMoveRight(currentControlBlock);
                    c.execute();
                }
                // rotate block
            }
        }
    }

    private boolean isGameOver() {
        return board.blockOverCeil();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
