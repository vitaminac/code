package core;

public interface Deque<E> extends Enumerable<E> {
    int size();

    boolean isEmpty();

    E getFirst();

    E getLast();

    void addFirst(E element);

    void addLast(E element);

    E removeFirst();

    E removeLast();
}
