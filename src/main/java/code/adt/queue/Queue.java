package code.adt.queue;

public interface Queue<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    E peek();

    void enqueue(E element);

    E dequeue();
}