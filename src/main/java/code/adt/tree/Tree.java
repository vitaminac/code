package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

public interface Tree<E> extends Enumerable<Position<E>> {
    int size();

    boolean isEmpty();

    Position<E> root();

    void root(E element);

    Position<E> parent(Position<E> position);

    E replace(Position<E> position, E element);

    E remove(Position<E> position);

    boolean isInternal(Position<E> position);

    boolean isLeaf(Position<E> position);
}
