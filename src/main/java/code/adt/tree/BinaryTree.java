package code.adt.tree;

import code.adt.Position;

public interface BinaryTree<E, P extends Position<E>> extends Tree<E, P> {

    P left(P position);

    P right(P position);

    boolean hasLeft(P position);

    boolean hasRight(P position);

    P sibling(P p);

    P left(P position, E element);

    P right(P position, E element);

    @Override
    default boolean isInternal(P position) {
        return this.hasLeft(position) || this.hasRight(position);
    }

    @Override
    default boolean isLeaf(P position) {
        return !this.isInternal(position);
    }
}
