package code.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class DoublyLinkedList<E> extends AbstractOrderedCollection<E> implements Deque<E>, Bag<E> {
    private static class LinkedNode<E> {
        private E element;
        private LinkedNode<E> next;
        private LinkedNode<E> prev;

        private LinkedNode(E element) {
            this.element = element;
        }
    }

    private LinkedNode<E> head;
    private LinkedNode<E> tail;

    public DoublyLinkedList() {
    }

    public DoublyLinkedList(Enumerable<E> enumerable) {
        enumerable.forEach(this::addLast);
    }

    @Override
    public int size() {
        int n = 0;
        LinkedNode<E> node = this.head;
        while (node != null) {
            n++;
            node = node.next;
        }
        return n;
    }

    @Override
    public void add(E item) {
        this.push(item);
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public E first() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.head.element;
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.tail.element;
    }

    @Override
    public void addFirst(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) {
            this.tail = node;
        } else {
            this.head.prev = node;
            node.next = this.head;
        }
        this.head = node;
    }

    @Override
    public void addLast(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) {
            this.head = node;
        } else {
            this.tail.next = node;
            node.prev = this.tail;
        }
        this.tail = node;
    }

    @Override
    public void removeFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.next;
            this.head.prev = null;
        }
    }

    @Override
    public void removeLast() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        LinkedNode<E> node = this.head;
        while (node != null) {
            consumer.accept(node.element);
            node = node.next;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedNode<E> next = DoublyLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                E item = next.element;
                this.next = next.next;
                return item;
            }
        };
    }
}
