package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

public interface Tree<E> extends Enumerable<Position<E>> {
    int size();

    // remove isEmpty use tree as node
    boolean isEmpty();

    Position<E> root();

    void root(E element);

    Position<E> parent(Position<E> position);

    E replace(Position<E> position, E element);

    E remove(Position<E> position);

    boolean isInternal(Position<E> position);

    boolean isLeaf(Position<E> position);

    Enumerable<Position<E>> preOrder();

    Enumerable<Position<E>> postOrder();
}
