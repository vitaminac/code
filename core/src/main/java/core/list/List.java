package core.list;

import core.behaviour.OrderedCollection;
import core.behaviour.RandomAccess;

public interface List<E> extends OrderedCollection<E>, RandomAccess<E> {
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
