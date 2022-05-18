import javax.swing.*;
import java.awt.*;

public class GameFrame extends JPanel {

    private Game game;
    private Game.Controller controller;

    private JPanel titlePanel = new JPanel();
    private JPanel gamePanel = new JPanel();
    private JPanel queuePanel = new JPanel();

    JLabel queueText = new JLabel();

    public GameFrame(Game game) {
        this.game = game;
        this.controller = game.getController();

        this.game.setFrameObserver(this);

        // setPreferredSize(new Dimension(game.getBoardSizeX(), game.getBoardSizeY()));
        // setBackground(Color.GREEN);

        setLayout(new BorderLayout());
        titlePanel.setLayout(new BorderLayout());
        gamePanel.setLayout(new BorderLayout());
        queuePanel.setLayout(new BorderLayout());

        repaint();

        titlePanel.add(new JLabel(game.getTitle()), BorderLayout.CENTER);
        gamePanel.add(game, BorderLayout.CENTER);

        Block nextBlock = game.getBlockGenerate().getQueue().get(0);
        queueText.setText("Next Block: " + nextBlock.toString());
        queuePanel.add(queueText, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(queuePanel, BorderLayout.SOUTH);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // paintFrame(g);
    }

    public void paintFrame(Graphics g) {
        g.setColor(Color.BLUE);
        //System.out.println(gamePanel.getX() + ", " + gamePanel.getY());
        g.fillRect(gamePanel.getX()-10, gamePanel.getY()-10, game.getBoardSizeX() * Game.GridUi.CELL_PIXEL_SIZE + 10,
                game.getBoardSizeY() * Game.GridUi.CELL_PIXEL_SIZE + 10);
    }

    public void update() {
        Block nextBlock = game.getBlockGenerate().getQueue().get(0);
        queueText.setText("Next Block: " + nextBlock.toString());
        repaint();
    }

    public Game getGame() {
        return game;
    }

    public Game.Controller getController() {
        return controller;
    }
}

