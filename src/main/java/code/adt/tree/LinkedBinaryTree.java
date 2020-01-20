package code.adt.tree;

import code.adt.Position;

import java.util.NoSuchElementException;

public class LinkedBinaryTree<E> implements BinaryTree<E> {
    public class BTNode implements Position<E> {

        private E element;
        private BTNode left, right, parent;

        public BTNode(E element, BTNode parent, BTNode left, BTNode right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public E getElement() {
            return this.element;
        }

        private int size() {
            int size = 1;
            if (this.left != null) size += this.left.size();
            if (this.right != null) size += this.right.size();
            return size;
        }
    }

    private BTNode root;

    public LinkedBinaryTree() {
        root = null;
    }

    private BTNode check(Position<E> position) {
        return (BTNode) position;
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
    public boolean hasLeft(Position<E> p) {
        return this.check(p).left != null;
    }

    @Override
    public boolean hasRight(Position<E> p) {
        return this.check(p).left.right != null;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public void root(E element) {
        if (root == null) this.root = new BTNode(element, null, null, null);
        else this.root.element = element;
    }

    @Override
    public Position<E> left(Position<E> position) throws RuntimeException {
        return this.check(position).left;
    }

    @Override
    public Position<E> right(Position<E> position) throws RuntimeException {
        return this.check(position).right;
    }

    @Override
    public Position<E> parent(Position<E> position) throws RuntimeException {
        return this.check(position).parent;
    }

    @Override
    public E replace(Position<E> position, E e) {
        BTNode node = this.check(position);
        E retVal = node.element;
        node.element = e;
        return retVal;
    }

    @Override
    public E remove(Position<E> position) {
        var node = this.check(position);
        if (node == this.root) {
            this.root = null;
        } else {
            if (node.parent.left == position) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
        return node.element;
    }


    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        BTNode parent = this.check(this.parent(p));
        if (parent == null) return null;
        if (this.hasLeft(parent) && this.left(parent) == p) return this.right(p);
        if (this.hasRight(parent) && this.right(parent) == p) return this.left(p);
        throw new NoSuchElementException();
    }


    @Override
    public Position<E> left(Position<E> p, E e) {
        var node = this.check(p);
        if (node.left == null) node.left = new BTNode(e, node, null, null);
        else node.left.element = e;
        return node.left;
    }

    @Override
    public Position<E> right(Position<E> p, E e) throws RuntimeException {
        var node = this.check(p);
        if (node.right == null) node.right = new BTNode(e, node, null, null);
        else node.right.element = e;
        return node.right;
    }
}
