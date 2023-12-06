package collections.linkedlist;

import core.behaviour.OrderedCollection;

public interface Steque<E> extends OrderedCollection<E> {
    E peek();

    void push(E element);

    E pop();

    void append(E element);
}
