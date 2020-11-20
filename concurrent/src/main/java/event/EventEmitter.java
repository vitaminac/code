package event;

import observer.Subject;
import observer.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class EventEmitter<E> implements Subject<E> {
    private final Set<Subscriber<? super E>> subscribers = new HashSet<>();

    @Override
    public synchronized void publish(E event) {
        for (var subscriber : this.subscribers) {
            subscriber.notify(event);
        }
    }

    @Override
    public synchronized void subscribe(Subscriber<? super E> subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber<? super E> subscriber) {
        this.subscribers.remove(subscriber);
    }
}
