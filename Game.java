import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game extends JPanel{
    public static final int PORT = 5455;
    public static final String IP = "127.0.0.1";
    private Client client;
    // แก้เป็น JPanel
    private String title;
    private int boardSizeX = 10;
    private int boardSizeY = 15;
    private GridUi gridUi;
    private Thread thread;
    private Controller controller;
    private long delayed = 200;
    private boolean gameOver;
    private String gameMode;

    GameFrame frameObserver;

    private Board board;

    private BlockGenerator blockGenerate;

    private Block currentControlBlock = null;

    public Game(String title, String gameMode) {
        if (Objects.equals(gameMode, "MultiPlayer")) {
            client = new Client();
            client.getKryo().register(BoardMessage.class);
            client.getKryo().register(Cell.class);
            client.getKryo().register(Block.class);
            client.getKryo().register(EventMessage.class);
            client.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    super.received(connection, object);
                    if (object instanceof BoardMessage) {
                        // ส่งไปให้ gameBoard
                    }
                }
            });
        }


        this.title = title;
        this.gameMode = gameMode;

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
        if (Objects.equals(gameMode, "MultiPlayer")) {
            client.start();
            try {
                client.connect(5000, IP, PORT);
            } catch (IOException e) {
                System.out.println("Cannot connect to the server!");
            }
        }
        setVisible(true);

        thread = new Thread() {
            @Override
            public void run() {
                if (Objects.equals(gameMode, "MultiPlayer")) {
                    // เริ่มเกม
                }
                addBlock(0, 0);
                while(!gameOver) {
                    // update
                    board.updateBoard();
                    gridUi.repaint();
                    // ส่งอัพเดทไป server
                    if (Objects.equals(gameMode, "MultiPlayer")) {
                        BoardMessage boardMessage = new BoardMessage();
                        boardMessage.senderTitle = title;
                        boardMessage.cells = board.getCells();
                        boardMessage.nextBlock = blockGenerate.getQueue().get(0);
                        client.sendTCP(boardMessage);
                    }

                    // game logic
                    // ถ้า currentControlBlock นิ่งแล้วให้ extract block มาใหม่
                    // แล้ว set currentControlBlock ใหม่
                    // รอเอาโค้ด Block fall
                    if (currentControlBlock.isStopFall()) {
                        addBlock(0, 0);
                        notifyFrameObserver();
                    }

                    gameOver = isGameOver();

                    waitFor(delayed);
                }
                // after finish game
                if (Objects.equals(gameMode, "MultiPlayer")) {
                    // send event ว่า game over
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

    public void setFrameObserver(GameFrame frameObserver) {
        this.frameObserver = frameObserver;
    }

    public GameFrame getFrameObserver() {
        return frameObserver;
    }

    public void notifyFrameObserver() {
        frameObserver.update();
    }

    public static void main(String[] args) {
        Game game = new Game("Player 1", "SinglePlayer");
        game.start();
    }
}
