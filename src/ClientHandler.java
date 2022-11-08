import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientHandler {
    private final Socket client;
    private final int clientID;

    private ObjectInputStream ois;
    private ObjectOutputStream ous;

    private BlockingQueue<Message> clientMessages;

    private ClientReader clientReader;
    private ClientWriter clientWriter;
    public ClientHandler(Socket client, int clientID) {
        this.client = client;
        this.clientID = clientID;
        clientMessages = new LinkedBlockingDeque<>();

        clientReader = new ClientReader();
        clientReader.start();
    }

    private class ClientReader extends Thread{
        @Override
        public void run() {
            try {
                ous = new ObjectOutputStream(client.getOutputStream());
                ous.flush();
                ois = new ObjectInputStream(client.getInputStream());

                 clientWriter = new ClientWriter();
                 clientWriter.start();

                Message msg;
                do {
                    msg = (Message) ois.readObject();
                    msg.apply(ClientHandler.this);
                }while (true);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                if (clientWriter != null)  {
                    clientMessages.add(new Quit());
                    System.out.println(">>> QUIT -> " + clientID);
                    clientWriter.interrupt();
                }
            }
        }
    }

    private class ClientWriter extends Thread {
        @Override
        public void run() {
            try {
                do {
                    Message message = clientMessages.take();
                    ous.writeObject(message);
                    ous.flush();

                }while (true);
            } catch (InterruptedException e) {
                e.printStackTrace();
                clientWriter = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void publish(Message<ClientHandler> message) {
        clientMessages.add(message);
    }

    public void quit() {
        clientReader.interrupt();
    }

    public int getClientID() {
        return clientID;
    }
}
