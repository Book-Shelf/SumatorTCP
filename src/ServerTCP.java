import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    ServerSocket server;
    Socket client;
    
    ServerTCP() {
        try {
            server = new ServerSocket(2020);
        } catch (IOException ex) {
            System.err.println("Problem with opening server socket");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public void listen() {

        while(true) {
            try {
                client = server.accept();
            } catch (IOException ex) {
                System.err.println("Problem with accepting a connection");
                ex.printStackTrace();
            }

            try {
                new PrintStream(client.getOutputStream()).println("Odebrano połączenie");
                client.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
