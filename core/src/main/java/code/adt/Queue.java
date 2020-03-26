package code.adt;

public interface Queue<E> extends Enumerable<E> {
    boolean isEmpty();

    E head();

    void enqueue(E element);

    E dequeue();
}