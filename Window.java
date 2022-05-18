import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    private JPanel gameButtonPanel = new JPanel();
    private JPanel exitButtonPanel = new JPanel();

    private JButton singlePlayerGameButton = new JButton("Single player");
    private JButton multiPlayerGameButton = new JButton("Multi player");
    private JButton exitGameButton = new JButton("Exit");

    public Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
        pack();
    }

    public void start() {
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        gameButtonPanel.add(singlePlayerGameButton);
        gameButtonPanel.add(multiPlayerGameButton);

        add(gameButtonPanel, BorderLayout.NORTH);

        exitButtonPanel.add(exitGameButton);

        add(exitButtonPanel, BorderLayout.SOUTH);

        initButtons();
    }

    private void initButtons() {
        singlePlayerGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleGameBoard singleGameBoard = new SingleGameBoard();
                singleGameBoard.start();
            }
        });
        multiPlayerGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MultiGameBoard multiGameBoard = new MultiGameBoard();
                multiGameBoard.start();
            }
        });
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.start();
    }
}
