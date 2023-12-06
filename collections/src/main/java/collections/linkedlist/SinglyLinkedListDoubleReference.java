package collections.linkedlist;

import core.behaviour.OrderedCollection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedListDoubleReference<E>
        implements OrderedCollection<E> {
    private SinglyLinkedListNode<E> head;
    private SinglyLinkedListNode<E> tail;

    public SinglyLinkedListDoubleReference() {
    }

    public SinglyLinkedListDoubleReference(final SinglyLinkedListDoubleReference<E> list) {
        if (list.head != null) {
            this.head = new SinglyLinkedListNode<>(list.head.getElement());
            var from = list.head;
            var to = this.head;
            while (from.getNext() != null) {
                to.setNext(new SinglyLinkedListNode<>(from.getNext().getElement()));
                from = from.getNext();
                to = to.getNext();
            }
            this.tail = to;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public int size() {
        int n = 0;
        var current = this.head;
        while (current != null) {
            current = current.getNext();
            n += 1;
        }
        return n;
    }

    public E getFirst() {
        return this.head.getElement();
    }

    public void appendFirst(final E element) {
        final var node = new SinglyLinkedListNode<E>(element);
        if (this.isEmpty()) {
            this.tail = node;
        } else {
            node.setNext(this.head);
        }
        this.head = node;
    }

    public void appendLast(final E element) {
        final var node = new SinglyLinkedListNode<E>(element);
        if (this.isEmpty()) {
            this.head = node;
        } else {
            this.tail.setNext(node);
        }
        this.tail = node;
    }

    public E removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        final var result = this.head.getElement();
        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.getNext();
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private SinglyLinkedListNode<E> next = SinglyLinkedListDoubleReference.this.head;

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public E next() {
                if (this.next == null) throw new NoSuchElementException();
                final var item = this.next.getElement();
                this.next = this.next.getNext();
                return item;
            }
        };
    }
}
