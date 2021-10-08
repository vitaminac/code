package core.deque;

import core.behaviour.OrderedCollection;
import core.functional.Enumerable;

public interface Deque<E> extends OrderedCollection<E>, Enumerable<E> {
    E getFirst();

    E getLast();

    void addFirst(E element);

    void addLast(E element);

    E removeFirst();

    E removeLast();
}
