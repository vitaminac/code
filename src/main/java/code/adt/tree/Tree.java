package code.adt.tree;

import code.adt.Enumerable;

public interface Tree<E, SelfType extends Tree<E, SelfType>> {
    int height();

    int size();

    E getElement();

    void replace(E element);

    default boolean isInternal() {
        return !this.isLeaf();
    }

    boolean isLeaf();

    Enumerable<SelfType> preOrder();

    Enumerable<SelfType> postOrder();
}
