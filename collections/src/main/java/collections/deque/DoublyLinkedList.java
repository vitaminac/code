package collections.deque;

import core.functional.Enumerable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E>
        implements Deque<E> {
    private DoublyLinkedListNode<E> head;
    private DoublyLinkedListNode<E> tail;

    public DoublyLinkedList() {
    }

    public DoublyLinkedList(Enumerable<E> enumerable) {
        enumerable.forEach(this::addLast);
    }

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

    @Override
    public E getFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.head.getElement();
    }

    @Override
    public E getLast() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.tail.getElement();
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
