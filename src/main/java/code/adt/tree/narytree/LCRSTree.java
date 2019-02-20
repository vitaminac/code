package code.adt.tree.narytree;

import code.adt.Position;

import java.util.Iterator;

/**
 * A linked class for a tree where nodes have an arbitrary number of children.
 *
 * @param <E> the elements stored in the tree
 * @author Raul Cabido, Abraham Duarte, Jose Velez, Jesús Sánchez-Oro
 */
public class LCRSTree<E> implements NAryTree<E> {
    private static class TreeNode<E> implements Position<E> {
        private E element;

        public TreeNode(E element) {
            this.element = element;
        }

        @Override
        public E getElement() {
            return null;
        }
    }

    private TreeNode<E> root = null;

    @Override
    public int size() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> root() throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isInternal(Position<E> v) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isRoot(Position<E> v) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> setRoot(E e) {
        this.root = new TreeNode<>(e);
        return this.root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public E replace(Position<E> p, E e) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void remove(Position<E> p) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }
}
