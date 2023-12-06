package collections.linkedlist;

import core.behaviour.OrderedCollection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E>
        implements OrderedCollection<E> {
    private DoublyLinkedListNode<E> head;
    private DoublyLinkedListNode<E> tail;

    @Override
    public int size() {
        int n = 0;
        DoublyLinkedListNode<E> node = this.head;
        while (node != null) {
            n++;
            node = node.getNext();
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    public E getFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.head.getElement();
    }

    public E getLast() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.tail.getElement();
    }

    public void addFirst(E element) {
        DoublyLinkedListNode<E> node = new DoublyLinkedListNode<E>(element);
        if (this.isEmpty()) {
            this.tail = node;
        } else {
            this.head.setPrev(node);
            node.setNext(this.head);
        }
        this.head = node;
    }

    public void addLast(E element) {
        DoublyLinkedListNode<E> node = new DoublyLinkedListNode<E>(element);
        if (this.isEmpty()) {
            this.head = node;
        } else {
            this.tail.setNext(node);
            node.setPrev(this.tail);
        }
        this.tail = node;
    }

    public E removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        final E tmp = this.head.getElement();
        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.getNext();
            this.head.setPrev(null);
        }
        return tmp;
    }

    public E removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        final E tmp = this.tail.getElement();
        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
        }
        return tmp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private DoublyLinkedListNode<E> next = DoublyLinkedList.this.head;

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
