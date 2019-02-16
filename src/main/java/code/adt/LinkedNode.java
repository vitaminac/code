package code.adt;

public class LinkedNode<E> {
    public E element;
    public LinkedNode<E> next;

    public LinkedNode(E element) {
        this.element = element;
    }
}
