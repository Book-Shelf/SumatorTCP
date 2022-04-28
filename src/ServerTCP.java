import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
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
                resend();
                client.close();
            } catch (IOException ex) {
                System.err.println("Problem with accepting a connection");
                ex.printStackTrace();
            }
        }
    }

    private void resend() {
        BufferedWriter writer;
        BufferedReader reader;
        String line;

        try {
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                writer.flush();
                System.out.println(line);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
