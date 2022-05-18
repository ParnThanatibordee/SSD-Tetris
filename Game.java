import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends JPanel {
    // แก้เป็น JPanel
    private String title;
    private int boardSizeX = 10;
    private int boardSizeY = 15;
    private GridUi gridUi;
    private Thread thread;
    private Controller controller;
    private long delayed = 200;
    private boolean gameOver;

    private Board board;

    private BlockGenerator blockGenerate;

    private Block currentControlBlock = null;

    public Game(String title) {
        this.title = title;

        controller = new Controller();
        addKeyListener(controller);

        board = new Board(boardSizeX, boardSizeY);
        blockGenerate = new BlockGenerator();

        gameOver = false;
        gridUi = new GridUi();
        add(gridUi);
        // ลบ pack กับ EXIT_ON_CLOSE ออก
        // pack();
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void start() {
        setVisible(true);

        thread = new Thread() {
            @Override
            public void run() {
                addBlock(0, 0);
                while(!gameOver) {
                    // update
                    board.updateBoard();
                    gridUi.repaint();
                    // game logic
                    // ถ้า currentControlBlock นิ่งแล้วให้ extract block มาใหม่
                    // แล้ว set currentControlBlock ใหม่
                    // รอเอาโค้ด Block fall
                    if (currentControlBlock.isStopFall()) {
                        addBlock(0, 0);
                    }

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
            paintBlock(g);
            paintFilledBlock(g);
            }

        private void paintCell(Graphics g, int row, int col) {
            int x = row * CELL_PIXEL_SIZE;
            int y = col * CELL_PIXEL_SIZE;

            g.setColor(Color.BLACK);
            g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x + 1, y + 1,
                    CELL_PIXEL_SIZE - 2, CELL_PIXEL_SIZE - 2);
        }

        private void paintBlock(Graphics g){
            ArrayList<Block> blocks = board.getBlocks();
            for (Block block: blocks) {
                block.render(g);
            }
        }

        private void paintFilledBlock(Graphics g){

        }
    }
  
    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // ใน control block (block) ต้องมีเช็คด้วยว่าถูก control อยู่รึเปล่า
            // currentControlBlock อาจจะเก็บใน game หรือ block factory
            if (currentControlBlock != null) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("*");
                    Command c = new CommandMoveDown(currentControlBlock);
                    c.execute();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    // check collision wall
                    Command c = new CommandMoveLeft(currentControlBlock);
                    c.execute();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    // check collision wall
                    Command c = new CommandMoveRight(currentControlBlock);
                    c.execute();
                }
                // rotate block
            }
        }
    }

    private void addBlock(int x, int y) {
        Block block = blockGenerate.extractBlock(x, y);
        board.getBlocks().add(block);

        currentControlBlock = block;
    }

    public String getTitle() {
        return title;
    }

    public Controller getController() {
        return controller;
    }

    public BlockGenerator getBlockGenerate() {
        return blockGenerate;
    }

    public int getBoardSizeX() {
        return boardSizeX;
    }

    public int getBoardSizeY() {
        return boardSizeY;
    }

    private boolean isGameOver() {
        return board.blockOverCeil();
    }

    public static void main(String[] args) {
        Game game = new Game("Player 1");
        game.start();
    }
}
