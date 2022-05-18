import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends JFrame {
    private int boardSizeX = 10;
    private int boardSizeY = 20;
    private GridUi gridUi;
    private Thread thread;
    private long delayed = 200;
    private boolean gameOver;

    private Controller controller;

    private Board board;

    private BlockGenerator blockGenerate;

    private Block currentControlBlock = null;

    public Game() {
        addKeyListener(new Controller());

        board = new Board(boardSizeX, boardSizeY);
        blockGenerate = new BlockGenerator();

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
                addBlock(5, 0);
                while (!gameOver) {
                    // update
                    board.updateBoard();
                    System.out.println(currentControlBlock.isStopFall());
                    gridUi.repaint();
                    // game logic
                    // ถ้า currentControlBlock นิ่งแล้วให้ extract block มาใหม่
                    // แล้ว set currentControlBlock ใหม่
                    // รอเอาโค้ด Block fall
                    if (currentControlBlock.isStopFall()) {
                        addBlock(5, 0);

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
                    paintCell(g, i, j, Color.LIGHT_GRAY);
                }
            }
            paintBlock(g);
            paintFilledBlock(g);
        }

        private void paintCell(Graphics g, int row, int col, Color color) {
            int x = row * CELL_PIXEL_SIZE;
            int y = col * CELL_PIXEL_SIZE;

            g.setColor(Color.BLACK);
            g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
            g.setColor(color);
            g.fillRect(x + 1, y + 1,
                    CELL_PIXEL_SIZE - 2, CELL_PIXEL_SIZE - 2);
        }

        private void paintBlock(Graphics g) {
            ArrayList<Block> blocks = board.getBlocks();
            for (Block block : blocks) {
                BlockShape shape = block.getShape();
                for (int i = 0; i < shape.getHeight(); i++) {
                    for (int j = 0; j < shape.getWidth(); j++) {
                            if (shape.isBlock(i, j)) {
                                int xPos = (block.getX() + j) * Block.CELL_SIZE;
                                int yPos = (block.getY() + i) * Block.CELL_SIZE;

                                g.setColor(shape.getColor());
                                g.fillRect(xPos, yPos, Block.CELL_SIZE, Block.CELL_SIZE);
                                g.setColor(Color.WHITE);
                                g.drawRect(xPos, yPos, Block.CELL_SIZE, Block.CELL_SIZE);}
                    }
                }
            }
        }
        private void paintFilledBlock(Graphics g) {
            for (int col = 0; col < board.getHeight(); col++) {
                if (board.blockFullFillRow(col)) {
                    for (int row = 0; row < board.getWidth(); row++) {
                        paintCell(g, row, col, Color.RED);
                    }
                }
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
                    if (!board.collisionToBottom(currentControlBlock)) {
                        Command c = new CommandMoveDown(currentControlBlock);
                        c.execute();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (!board.collisiontoLeft(currentControlBlock)) {
                        Command c = new CommandMoveLeft(currentControlBlock);
                        c.execute();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        // check collision wall
                    if (!board.collisionToRight(currentControlBlock)) {
                        Command c = new CommandMoveRight(currentControlBlock);
                        c.execute();
                    }
                    // rotate block
                }
            }
        }
    }


        private void addBlock(int x, int y) {
            Block block = blockGenerate.extractBlock(x, y);
            board.getBlocks().add(block);

            currentControlBlock = block;
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
            return board.collisionToTop(currentControlBlock);
        }
        public static void main(String[] args) {
            Game game = new Game();
            game.start();
        }
    }