package core.dp;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * this class is not thread safe
 *
 * @param <Event>
 */
public class Subject<Event> {
    private final Set<Consumer<? super Event>> subscribers = new HashSet<>();

    public void subscribe(Consumer<? super Event> subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(Consumer<? super Event> subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void publish(Event event) {
        for (var subscriber : this.subscribers) {
            subscriber.accept(event);
        }
    }
}
