package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

public interface Tree<E, P extends Position<E>> extends Enumerable<P> {
    int size();

    boolean isEmpty();

    P root();

    void root(E element);

    P parent(P position);

    E replace(P position, E element);

    E remove(P position);

    boolean isInternal(P position);

    boolean isLeaf(P position);
}
