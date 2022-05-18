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
                        frameObserver.getOnMultiGameBoard().setCells(((BoardMessage) object).cells);
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
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.senderTitle = title;
                    eventMessage.actionText = "start the game";
                    client.sendTCP(eventMessage);
                }
                addBlock(0, 0);
                while(!gameOver) {
                    // update
                    board.updateBoard();
                    gridUi.repaint();
                    // send update to server
                    if (Objects.equals(gameMode, "MultiPlayer")) {
                        BoardMessage boardMessage = new BoardMessage();
                        boardMessage.senderTitle = title;
                        boardMessage.cells = board.getCells();
                        boardMessage.nextBlock = blockGenerate.getQueue().get(0);
                        client.sendTCP(boardMessage);
                    }

                    // game logic
                    if (currentControlBlock.isStopFall()) {
                        addBlock(0, 0);
                        notifyFrameObserver();
                    }

                    gameOver = isGameOver();

                    waitFor(delayed);
                }
                // after finish game
                if (Objects.equals(gameMode, "MultiPlayer")) {
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.senderTitle = title;
                    eventMessage.actionText = "game over";
                    client.sendTCP(eventMessage);
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
                }
            }
        }
    }

    private void addBlock(int x, int y) {
        Block block = blockGenerate.extractBlock(x, y);
        board.getBlocks().add(block);

        currentControlBlock = block;
    }

    public Board getBoard() {
        return board;
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