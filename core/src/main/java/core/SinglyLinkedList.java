package core;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class SinglyLinkedList<E> implements Stack<E>, Queue<E> {
    private static class LinkedNode<E> {
        private E element;
        private LinkedNode<E> next;

        private LinkedNode(E element) {
            this.element = element;
        }
    }

    private LinkedNode<E> head;
    private LinkedNode<E> tail;

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(Enumerable<E> enumerable) {
        enumerable.forEach(this::push);
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
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public E top() {
        return this.head();
    }

    @Override
    public E head() {
        return this.head.element;
    }

    @Override
    public void push(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) {
            this.tail = node;
        } else {
            node.next = this.head;
        }
        this.head = node;
    }

    @Override
    public void enqueue(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) {
            this.head = node;
        } else {
            this.tail.next = node;
        }
        this.tail = node;
    }

    @Override
    public E dequeue() {
        if (this.isEmpty()) throw new NoSuchElementException();
        E retVal = this.head.element;
        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.next;
        }
        return retVal;
    }

    @Override
    public E pop() {
        return this.dequeue();
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
            private LinkedNode<E> next = SinglyLinkedList.this.head;

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
