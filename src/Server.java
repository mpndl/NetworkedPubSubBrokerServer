import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        new Server();
    }

    Server() throws IOException {
        int clientID = 0;
        ServerSocket server = new ServerSocket(5050);
        System.out.printf(">>> RUNNING -> %d", server.getLocalPort());

        do {
            Socket client = server.accept();
            clientID++;

            System.out.println("\n>>> CONNECTED -> " + clientID);

            new ClientHandler(client, clientID);
        } while (true);
    }
}
