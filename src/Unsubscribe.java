import java.io.Serial;
import java.net.Socket;

public class Unsubscribe extends Message<ClientHandler> {
    @Serial
    private static final long serialVersionUID = 3L;

    private final String topic;
    private final Subscriber subscriber;

    public Unsubscribe(String topic, Subscriber subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
    }

    @Override
    public void apply(ClientHandler handler) {
        if (topic != null)
            PubSubBroker.unsubscribe(topic, subscriber);
        else PubSubBroker.unsubscribe(subscriber);
        System.out.println(">>> UNSUBSCRIBED -> " + topic);
    }

    @Override
    public String toString() {
        return "Unsubscribe{" +
                "topic='" + topic + '\'' +
                '}';
    }
}
