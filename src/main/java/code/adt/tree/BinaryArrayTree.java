package code.adt.tree;

import code.adt.ArrayList;
import code.adt.Position;

import java.util.function.Consumer;

public class BinaryArrayTree<E> implements BinaryTree<E, BinaryArrayTree.BTNode<E>> {
    public static class BTNode<E> implements Position<E> {
        private E element;
        private int index;

        public BTNode(E element, int index) {
            this.element = element;
            this.index = index;
        }

        @Override
        public E getElement() {
            return this.element;
        }
    }

    private ArrayList<BTNode<E>> tree = new ArrayList<>();

    @Override
    public BTNode<E> left(BTNode<E> position) {
        return this.tree.get(position.index * 2 + 1);
    }

    @Override
    public BTNode<E> right(BTNode<E> position) {
        return this.tree.get(position.index * 2 + 2);
    }

    @Override
    public boolean hasLeft(BTNode<E> position) {
        int index = position.index * 2 + 1;
        return this.tree.size() > index && this.tree.get(index) != null;
    }

    @Override
    public boolean hasRight(BTNode<E> position) {
        int index = position.index * 2 + 2;
        return this.tree.size() > index && this.tree.get(index) != null;
    }

    @Override
    public BTNode<E> sibling(BTNode<E> position) {
        if (position.index % 2 == 0) {
            return this.tree.get(position.index - 1);
        } else {
            return this.tree.get(position.index + 1);
        }
    }

    @Override
    public BTNode<E> left(BTNode<E> position, E element) {
        int index = position.index * 2 + 1;
        BTNode<E> node = new BTNode<>(element, index);
        this.tree.set(index, node);
        return node;
    }

    @Override
    public BTNode<E> right(BTNode<E> position, E element) {
        int index = position.index * 2 + 2;
        BTNode<E> node = new BTNode<>(element, index);
        this.tree.set(index, node);
        return node;
    }

    private int countDescent(BTNode<E> node) {
        if (this.hasLeft(node)) {
            if (this.hasRight(node)) {
                return this.countDescent(this.left(node)) + this.countDescent(this.right(node)) + 1;
            }
            return this.countDescent(this.left(node)) + 1;
        }
        return 1;
    }

    @Override
    public int size() {
        return this.countDescent(this.tree.get(0));
    }

    @Override
    public boolean isEmpty() {
        return this.tree.get(0) == null;
    }

    @Override
    public BTNode<E> root() {
        return this.tree.get(0);
    }

    @Override
    public void root(E element) {
        if (this.tree.get(0) == null) {
            this.tree.set(0, new BTNode<>(element, 0));
        } else {
            this.tree.get(0).element = element;
        }
    }

    @Override
    public BTNode<E> parent(BTNode<E> position) {
        return this.tree.get((position.index - 1) / 2);
    }

    @Override
    public E replace(BTNode<E> position, E element) {
        E retVal = position.element;
        position.element = element;
        return retVal;
    }

    @Override
    public E remove(BTNode<E> position) {
        this.tree.set(position.index, null);
        return position.getElement();
    }

    @Override
    public void enumerate(Consumer<BTNode<E>> consumer) {
        new InOrderTraversal<>(this).enumerate(consumer);
    }
}
