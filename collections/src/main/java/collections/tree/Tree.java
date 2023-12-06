package collections.tree;

import core.functional.Enumerable;

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
