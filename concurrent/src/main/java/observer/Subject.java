package observer;

public interface Subject<Event> {
    void subscribe(Subscriber<? super Event> subscriber);

    void unsubscribe(Subscriber<? super Event> subscriber);

    void publish(Event event);
}
