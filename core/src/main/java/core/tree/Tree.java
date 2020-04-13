package core.tree;

import core.Enumerable;

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
