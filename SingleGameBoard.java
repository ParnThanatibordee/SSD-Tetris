// SinglePlayer

import javax.swing.*;
import java.awt.*;

public class SingleGameBoard extends JFrame {
    private int gameBoardSizeX = 400;
    private int gameBoardSizeY = 525;

    private JPanel gamePanel = new JPanel();
    private Game game;

    public SingleGameBoard() {
        setPreferredSize(new Dimension(gameBoardSizeX, gameBoardSizeY));
        setLayout(new BorderLayout());

        game = new Game();
        gamePanel.setLayout(new BorderLayout());
        JLabel label1 = new JLabel("Player 1");
        gamePanel.add(label1, BorderLayout.NORTH);
        gamePanel.add(game, BorderLayout.CENTER);
        addKeyListener(game.getController());
        game.start();

        add(gamePanel, BorderLayout.WEST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }

    public void start() {
        setVisible(true);
    }
}
