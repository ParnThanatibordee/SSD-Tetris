import javax.swing.*;
import java.awt.*;

public class MultiGameBoard extends JFrame {
    private static int gameBoardSizeX = 900;
    private static int gameBoardSizeY = 600;

    private Game game1;
    private GameFrame gameFrame1;
    private GameListener game2;
    private GameFrame gameFrame2;


    public MultiGameBoard() {
        setPreferredSize(new Dimension(gameBoardSizeX, gameBoardSizeY));
        setLayout(new BorderLayout());

        game1 = new Game("Player 1", "MultiPlayer");
        gameFrame1 = new GameFrame(game1);
        add(gameFrame1, BorderLayout.WEST);
        addKeyListener(game1.getController());
        game1.start();

        game2 = new GameListener("Player 2", "MultiPlayer");
        gameFrame2 = new GameFrame(game2);
        add(gameFrame2, BorderLayout.EAST);
        game2.start();

        // update Board

        setResizable(false);
        pack();
    }

    public void start() {
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
