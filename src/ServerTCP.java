import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    private final int bufferLen = 64;

    private ServerSocket server;
    private Socket client;
    private char[] buffer = new char[bufferLen];
    private Adder adder = new Adder();
    
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
                resend();
                System.out.println("Closing...");
                client.close();
            } catch (IOException ex) {
                System.err.println("Problem with accepting a connection");
                ex.printStackTrace();
            }
        }
    }

    private void resend() {
        BufferedWriter writer;
        InputStreamReader reader;
        int readChar;

        try {
            reader = new InputStreamReader(client.getInputStream());
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            while ((readChar = reader.read(buffer, 0, bufferLen)) != -1) {
                adder.getSum(buffer, readChar);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
