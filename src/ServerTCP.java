import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    private final int bufferLen = 64;

    private ServerSocket server;
    private char[] buffer = new char[bufferLen];
    
    ServerTCP() {
        try {
            server = new ServerSocket(2020);
        } catch (IOException ex) {
            System.err.println("Problem with creating server socket");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private class ClientWork implements Runnable {
        Socket client;
        private Adder adder = new Adder();

        ClientWork(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                processMessages(client);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("Closing...");
            }
        }
        
        private void processMessages(Socket client) throws IOException {
            BufferedWriter writer = null;
            InputStreamReader reader = null;
            int readChar;
    
            try {
                reader = new InputStreamReader(client.getInputStream());
                writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    
                while ((readChar = reader.read(buffer, 0, bufferLen)) != -1) {
                    adder.calculateSum(buffer, readChar);

                    if (!adder.getResults().isEmpty()) {
                        for (String result : adder.getResults()) {
                            writer.write(result);
                            writer.flush();
                        }
    
                        adder.clearResults();
                    }
                }
    
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                } 
    
                if (writer != null) {
                    writer.close();
                }
    
                if (client != null) {
                    client.close();
                }
            }
        }
    }

    public void listen() {
        Socket client;

        while(true) {
            try {
                client = server.accept();
                new Thread(new ClientWork(client)).start();;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
