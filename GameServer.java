import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends JFrame{
    public static final int PORT = 5455;

    private Server server;
    private JTextArea screen;
    private int clientConnection = 0;
    private final int maxClientConnection = 2;
    private int player1;
    private int player2;

    public GameServer() {
        server = new Server();

        server.getKryo().register(BoardMessage.class);
        server.getKryo().register(Cell.class);
        server.getKryo().register(Block.class);
        server.getKryo().register(EventMessage.class);
        server.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                super.received(connection, object);
                if (object instanceof BoardMessage) {
                    BoardMessage boardMessage = (BoardMessage) object;

                    if (connection.getID() == player1) {
                        server.sendToTCP(player2, boardMessage);
                        // ส่งไปให้ player2 player 2 ส่งให้ game board
                        // game ส่ง
                        // board รับ
                    } else if (connection.getID() == player2) {
                        server.sendToTCP(player1, boardMessage);
                    }
                } else if (object instanceof EventMessage) {
                    EventMessage eventMessage = (EventMessage) object;
                    screen.append(eventMessage.senderTitle + " : " + eventMessage.actionText + "\n");
                }
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                screen.append("New client connected.\n");
                clientConnection += 1;
                if (clientConnection == 1) {
                    player1 = connection.getID();
                } else if (clientConnection == 2) {
                    player2 = connection.getID();
                }
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                screen.append("Client disconnected.\n");
                clientConnection -= 1;
                if (connection.getID() == player1) {
                    player1 = 0;
                } else if (connection.getID() == player2) {
                    player2 = 0;
                }
            }
        });
        initGui();
    }

    public void start() {
        setVisible(true);
        screen.append("Sever Started\n");

        server.start();
        try {
            server.bind(PORT);
        } catch (IOException e) {
            screen.append("Cannot bind to the port " + PORT);
            e.printStackTrace();
        }
    }

    private void initGui() {
        setTitle("Server Screen");
        // setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        screen = new JTextArea();
        screen.setPreferredSize(new Dimension(480, 480));
        screen.setBackground(Color.BLACK);
        screen.setForeground(Color.GREEN);
        screen.setEditable(false);
        add(screen);
        pack();
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.start();
    }
}
