package core.tree;

public class LinkedBinaryTree<E> implements BinaryTree<E, LinkedBinaryTree<E>> {
    private E element;
    private LinkedBinaryTree<E> left, right;

    public LinkedBinaryTree(E element) {
        this.element = element;
    }

    @Override
    public LinkedBinaryTree<E> left() {
        return this.left;
    }

    @Override
    public LinkedBinaryTree<E> right() {
        return right;
    }

    @Override
    public void left(LinkedBinaryTree<E> tree) {
        this.left = tree;
    }

    @Override
    public void right(LinkedBinaryTree<E> tree) {
        this.right = tree;
    }

    @Override
    public E getElement() {
        return this.element;
    }

    @Override
    public void replace(E element) {
        this.element = element;
    }
}
