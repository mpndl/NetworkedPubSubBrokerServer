import java.io.Serial;

public class Subscribe extends Message<ClientHandler> {
    @Serial
    private static final long serialVersionUID = 2L;

    private final String topic;
    public final Subscriber subscriber;

    public Subscribe(String topic, Subscriber subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
    }

    @Override
    public void apply(ClientHandler handler) {
        Subscriber subscriber = (publisher, topic1, params) -> {
            Message<ClientHandler> message = new Publish(publisher, topic1, params);
            handler.publish(message);
        };
        System.out.println(">>> SUBSCRIBED -> " + topic);
        PubSubBroker.subscribe(topic, subscriber);
    }

    @Override
    public String toString() {
        return "Subscribe{" +
                "topic='" + topic + '\'' +
                '}';
    }
}
