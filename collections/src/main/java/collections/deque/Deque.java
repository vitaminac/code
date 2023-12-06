package collections.deque;

import core.behaviour.OrderedCollection;

public interface Deque<E> extends OrderedCollection<E> {
    E getFirst();

    E getLast();

    void addFirst(E element);

    void addLast(E element);

    E removeFirst();

    E removeLast();
}
