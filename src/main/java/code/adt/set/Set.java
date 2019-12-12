package code.adt.set;

import code.adt.Enumerable;

public interface Set<E> extends Enumerable<E> {
    boolean isEmpty();

    boolean contains(E element);

    void add(E element);

    void remove(E element);

    void clear();
}
