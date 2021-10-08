package core.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class SinglyLinkedListDoubleReference<E> implements Iterable<E> {
    private static class LinkedNode<E> {
        private final E element;
        private LinkedNode<E> next;

        private LinkedNode(E element) {
            this.element = element;
        }
    }

    private LinkedNode<E> head;
    private LinkedNode<E> tail;

    public int size() {
        int n = 0;
        LinkedNode<E> node = this.head;
        while (node != null) {
            n++;
            node = node.next;
        }
        return n;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public E peek() {
        return this.head.element;
    }

    public void prependHead(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) {
            this.tail = node;
        } else {
            node.next = this.head;
        }
        this.head = node;
    }

    public void appendTail(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) {
            this.head = node;
        } else {
            this.tail.next = node;
        }
        this.tail = node;
    }

    public E removeHead() {
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
            private LinkedNode<E> next = SinglyLinkedListDoubleReference.this.head;

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public E next() {
                if (this.next == null) throw new NoSuchElementException();
                final var item = this.next.element;
                this.next = this.next.next;
                return item;
            }
        };
    }
}
