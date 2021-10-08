package core;

import core.behaviour.RandomAccess;
import core.functional.Iterable;

public interface List<E> extends Iterable<E>, RandomAccess<E> {
    boolean isEmpty();

    int size();

    void clear();

    // TODO: rename to insert
    void add(int index, E element);

    E remove(int index);

    int indexOf(E element);

    int lastIndexOf(E element);

    default void swap(int i, int j) {
        this.set(i, this.set(j, this.get(i)));
    }
}
