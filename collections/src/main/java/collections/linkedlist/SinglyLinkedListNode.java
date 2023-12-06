package collections.linkedlist;

public class SinglyLinkedListNode<E> {
    private final E element;
    private SinglyLinkedListNode<E> next;

    public SinglyLinkedListNode(E element) {
        this.element = element;
    }

    public E getElement() {
        return this.element;
    }

    public SinglyLinkedListNode<E> getNext() {
        return this.next;
    }

    public void setNext(final SinglyLinkedListNode<E> next) {
        this.next = next;
    }
}
