package code.adt;

public interface PriorityQueue<E> extends Iterable<E> {
    void add(E element);

    E peek();

    int size();

    boolean isEmpty();

    E remove();
}
