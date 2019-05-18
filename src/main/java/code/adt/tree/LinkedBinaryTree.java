package code.adt.tree;

import code.adt.Position;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class LinkedBinaryTree<E> implements BinaryTree<E, LinkedBinaryTree.BTNode<E>> {
    public static class BTNode<T> implements Position<T> {

        private T element;
        private BTNode<T> left, right, parent;

        public BTNode(T element, BTNode<T> parent, BTNode<T> left, BTNode<T> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public T getElement() {
            return this.element;
        }

        private int size() {
            int size = 1;
            if (this.left != null) size += this.left.size();
            if (this.right != null) size += this.right.size();
            return size;
        }
    }

    private BTNode<E> root;

    public LinkedBinaryTree() {
        root = null;
    }

    @Override
    public int size() {
        if (this.isEmpty()) return 0;
        else return this.root.size();
    }

    @Override
    public boolean isEmpty() {
        return (root == null);
    }

    @Override
    public boolean hasLeft(BTNode<E> p) {
        return p.left != null;
    }

    @Override
    public boolean hasRight(BTNode<E> p) {
        return p.right != null;
    }

    @Override
    public BTNode<E> root() {
        return root;
    }

    @Override
    public void root(E element) {
        if (root == null) this.root = new BTNode<>(element, null, null, null);
        else this.root.element = element;
    }

    @Override
    public BTNode<E> left(BTNode<E> position) throws RuntimeException {
        return position.left;
    }

    @Override
    public BTNode<E> right(BTNode<E> position) throws RuntimeException {
        return position.right;
    }

    @Override
    public BTNode<E> parent(BTNode<E> position) throws RuntimeException {
        return position.parent;
    }

    @Override
    public E replace(BTNode<E> position, E e) {
        E retVal = position.element;
        position.element = e;
        return retVal;
    }

    @Override
    public E remove(BTNode<E> position) {
        if (position == this.root) {
            this.root = null;
        } else {
            if (position.parent.left == position) {
                position.parent.left = null;
            } else {
                position.parent.right = null;
            }
        }
        return position.element;
    }


    @Override
    public BTNode<E> sibling(BTNode<E> p) throws RuntimeException {
        BTNode<E> parent = this.parent(p);
        if (parent == null) return null;
        if (this.hasLeft(parent) && this.left(parent) == p) return this.right(p);
        if (this.hasRight(parent) && this.right(parent) == p) return this.left(p);
        throw new NoSuchElementException();
    }


    @Override
    public BTNode<E> left(BTNode<E> p, E e) {
        if (p.left == null) p.left = new BTNode<>(e, p, null, null);
        else p.left.element = e;
        return p.left;
    }

    @Override
    public BTNode<E> right(BTNode<E> p, E e) throws RuntimeException {
        if (p.right == null) p.right = new BTNode<>(e, p, null, null);
        else p.right.element = e;
        return p.right;
    }

    public void linkLeft(BTNode<E> position, BTNode<E> left) {
        position.left = left;
    }

    public void linkRight(BTNode<E> position, BTNode<E> right) {
        position.right = right;
    }

    @Override
    public void enumerate(Consumer<BTNode<E>> consumer) {
        new InOrderTraversal<>(this).enumerate(consumer);
    }
}
