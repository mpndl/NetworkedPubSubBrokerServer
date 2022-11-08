import java.io.Serial;
import java.net.Socket;
import java.util.Map;

public class Publish extends Message<ClientHandler>{
    @Serial
    private static final long serialVersionUID = 1L;

    private final Object publisher;
    private final String topic;
    private final Map<String,Object> params;

    public Publish(Object publisher, String topic, Map<String, Object> params) {
        this.publisher = publisher;
        this.topic = topic;
        this.params = params;
    }

    @Override
    public void apply(ClientHandler handler) {
        PubSubBroker.publish(publisher, topic, params);
        System.out.println(">>> PUBLISHED -> " + params.get("message"));
    }

    @Override
    public String toString() {
        return "Publish{" +
                "params=" + params.get("message") +
                '}';
    }
}
