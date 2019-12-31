package code.adt.tree;

import code.adt.Position;

public interface BinaryTree<E> extends Tree<E> {

    Position<E> left(Position<E> position);

    Position<E> right(Position<E> position);

    boolean hasLeft(Position<E> position);

    boolean hasRight(Position<E> position);

    Position<E> sibling(Position<E> p);

    Position<E> left(Position<E> position, E element);

    Position<E> right(Position<E> position, E element);

    @Override
    default boolean isInternal(Position<E> position) {
        return this.hasLeft(position) || this.hasRight(position);
    }

    @Override
    default boolean isLeaf(Position<E> position) {
        return !this.isInternal(position);
    }
}
