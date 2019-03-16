package code.adt;

public interface Queue<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    // TODO: head
    E peek();

    void enqueue(E element);

    E dequeue();
}