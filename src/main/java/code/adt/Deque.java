package code.adt;

public interface Deque<E> extends Iterable<E> {
    void addFirst(E element);

    void addLast(E element);

    E removeFirst();

    E removeLast();

    E first();

    E last();

    int size();

    boolean isEmpty();
}
