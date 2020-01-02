package code.adt.tree;

import code.adt.ArrayList;
import code.adt.Position;

import java.util.function.Consumer;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
    private class BTNode<T> implements Position<T> {
        private T element;
        private int index;

        public BTNode(T element, int index) {
            this.element = element;
            this.index = index;
        }

        @Override
        public T getElement() {
            return this.element;
        }
    }

    private ArrayList<BTNode<E>> tree = new ArrayList<>();

    private BTNode<E> check(Position<E> position) {
        if (!(position instanceof BTNode)) {
            throw new RuntimeException("The position is invalid");
        }
        return (BTNode<E>) position;
    }

    private BTNode<E> ensureGet(int index) {
        if (this.tree.size() > index) {
            return this.tree.get(index);
        } else {
            return null;
        }
    }

    private void ensureSet(int index, BTNode<E> node) {
        while (this.tree.size() <= index) {
            this.tree.insert(this.tree.size(), null);
        }
        this.tree.set(index, node);
    }

    @Override
    public Position<E> left(Position<E> position) {
        return this.ensureGet(this.check(position).index * 2 + 1);
    }

    @Override
    public Position<E> right(Position<E> position) {
        return this.ensureGet(this.check(position).index * 2 + 2);
    }

    @Override
    public boolean hasLeft(Position<E> position) {
        return this.ensureGet(this.check(position).index * 2 + 1) != null;
    }

    @Override
    public boolean hasRight(Position<E> position) {
        return this.ensureGet(this.check(position).index * 2 + 2) != null;
    }

    @Override
    public Position<E> sibling(Position<E> position) {
        BTNode<E> node = this.check(position);
        if (node.index % 2 == 0) {
            return this.ensureGet(node.index - 1);
        } else {
            return this.ensureGet(node.index + 1);
        }
    }

    @Override
    public Position<E> left(Position<E> position, E element) {
        int index = this.check(position).index * 2 + 1;
        BTNode<E> node = new BTNode<>(element, index);
        this.ensureSet(index, node);
        return node;
    }

    @Override
    public Position<E> right(Position<E> position, E element) {
        int index = this.check(position).index * 2 + 2;
        BTNode<E> node = new BTNode<>(element, index);
        this.ensureSet(index, node);
        return node;
    }

    private int countDescent(Position<E> node) {
        if (node == null) return 0;
        else return 1 + this.countDescent(this.left(node)) + this.countDescent(this.right(node));
    }

    @Override
    public int size() {
        return this.countDescent(this.root());
    }

    @Override
    public boolean isEmpty() {
        return this.root() == null;
    }

    @Override
    public Position<E> root() {
        return this.ensureGet(0);
    }

    @Override
    public void root(E element) {
        if (this.root() == null) {
            this.ensureSet(0, new BTNode<>(element, 0));
        } else {
            this.check(this.root()).element = element;
        }
    }

    @Override
    public Position<E> parent(Position<E> position) {
        return this.ensureGet((this.check(position).index - 1) / 2);
    }

    @Override
    public E replace(Position<E> position, E element) {
        BTNode<E> node = this.check(position);
        E retVal = node.element;
        node.element = element;
        return retVal;
    }

    @Override
    public E remove(Position<E> position) {
        this.ensureSet(this.check(position).index, null);
        return position.getElement();
    }

    @Override
    public void enumerate(Consumer<Position<E>> consumer) {
        new InOrderTraversal<>(this).enumerate(consumer);
    }
}
