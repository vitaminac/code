package collections.tree;

import core.functional.Enumerable;

public class LCRSTree<E> implements NAryTree<E, LCRSTree<E>> {
    private E element;
    private LCRSTree<E> leftChild;
    private LCRSTree<E> rightSibling;

    public LCRSTree(E element) {
        this.element = element;
    }

    @Override
    public void addChild(LCRSTree<E> tree) {
        if (this.leftChild == null) {
            this.leftChild = tree;
        } else {
            var prev = this.leftChild;
            while (prev.rightSibling != null) {
                prev = prev.rightSibling;
            }
            prev.rightSibling = tree;
        }
    }

    @Override
    public Enumerable<LCRSTree<E>> children() {
        return consumer -> {
            var child = this.leftChild;
            while (child != null) {
                consumer.accept(child);
                child = child.rightSibling;
            }
        };
    }

    @Override
    public E getElement() {
        return this.element;
    }

    @Override
    public void replace(E element) {
        this.element = element;
    }

    @Override
    public boolean isLeaf() {
        return this.leftChild == null;
    }
}
