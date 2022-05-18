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

    public GameServer() {
        server = new Server();
        List<BoardMessage> messageHistory = new ArrayList<BoardMessage>();

        server.getKryo().register(BoardMessage.class);
        server.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                //
            }

            @Override
            public void connected(Connection connection) {
                //
                clientConnection += 1;
            }

            @Override
            public void disconnected(Connection connection) {
                //
                clientConnection -= 1;
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
        setAlwaysOnTop(true);
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
        GameSever gameSever = new GameSever();
        gameSever.start();
    }
}
