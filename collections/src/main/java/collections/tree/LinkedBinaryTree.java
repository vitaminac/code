package collections.tree;

import core.functional.Enumerable;

public class LinkedBinaryTree<E> implements Tree<E, LinkedBinaryTree<E>> {
    private E element;
    private LinkedBinaryTree<E> left, right;

    public LinkedBinaryTree(E element) {
        this.element = element;
    }

    public LinkedBinaryTree<E> left() {
        return this.left;
    }

    public LinkedBinaryTree<E> right() {
        return right;
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
    public int height() {
        return Math.max(this.left() == null ? 0 : this.left().height(), this.right() == null ? 0 : this.right().height()) + 1;
    }

    @Override
    public int size() {
        int size = 1;
        if (this.left() != null) size += this.left().size();
        if (this.right() != null) size += this.right().size();
        return size;
    }

    @Override
    public boolean isLeaf() {
        return this.left() == null && this.right() == null;
    }

    @Override
    public Enumerable<LinkedBinaryTree<E>> preOrder() {
        return consumer -> {
            consumer.accept(this);
            if (this.left() != null) this.left().preOrder().forEach(consumer);
            if (this.right() != null) this.right().preOrder().forEach(consumer);
        };
    }

    @Override
    public Enumerable<LinkedBinaryTree<E>> postOrder() {
        return consumer -> {
            if (this.left() != null) this.left().postOrder().forEach(consumer);
            if (this.right() != null) this.right().postOrder().forEach(consumer);
            consumer.accept(this);
        };
    }

    public Enumerable<LinkedBinaryTree<E>> inOrder() {
        return consumer -> {
            if (this.left() != null) this.left().inOrder().forEach(consumer);
            consumer.accept(this);
            if (this.right() != null) this.right().inOrder().forEach(consumer);
        };
    }
}
