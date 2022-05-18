// SinglePlayer

import javax.swing.*;
import java.awt.*;

public class SingleGameBoard extends JFrame {
    private int gameBoardSizeX = 450;
    private int gameBoardSizeY = 600;

    private Game game;
    private GameFrame gameFrame;

    public SingleGameBoard() {
        setPreferredSize(new Dimension(gameBoardSizeX, gameBoardSizeY));
        setLayout(new BorderLayout());

        game = new Game("Player 1", "SinglePlayer");
        gameFrame = new GameFrame(game);
        add(gameFrame, BorderLayout.CENTER);
        addKeyListener(game.getController());
        game.start();

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
