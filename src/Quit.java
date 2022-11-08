import java.io.Serial;

public class Quit extends Message<ClientHandler> {
    @Serial
    private static final long serialVersionUID = 4L;

    @Override
    public void apply(ClientHandler handler) {
        System.out.println(">>> QUIT -> " + handler.getClientID());
        handler.quit();
    }

    @Override
    public String toString() {
        return "Quit";
    }
}
