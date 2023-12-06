package collections.linkedlist;

public class DoublyLinkedListNode<E> {
    private final E element;
    private DoublyLinkedListNode<E> next;
    private DoublyLinkedListNode<E> prev;

    public DoublyLinkedListNode(final E element) {
        this.element = element;
    }

    public E getElement() {
        return this.element;
    }

    public DoublyLinkedListNode<E> getNext() {
        return this.next;
    }

    public void setNext(final DoublyLinkedListNode<E> next) {
        this.next = next;
    }

    public DoublyLinkedListNode<E> getPrev() {
        return this.prev;
    }

    public void setPrev(final DoublyLinkedListNode<E> prev) {
        this.prev = next;
    }
}
