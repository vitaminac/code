package code.adt;

public interface Queue<E> extends Enumerable<E> {
    int size();

    boolean isEmpty();

    E head();

    void enqueue(E element);

    E dequeue();
}