package code.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements Bag<E>, Queue<E>, Stack<E> {
    private LinkedNode<E> head;
    private LinkedNode<E> tail;
    private int size = 0;

    public LinkedList() {
    }

    public LinkedList(Stack<E> stack) {
        while (!stack.isEmpty()) this.push(stack.pop());
    }

    public LinkedList(Queue<E> queue) {
        while (!queue.isEmpty()) this.enqueue(queue.dequeue());
    }

    @Override
    public void add(E item) {
        this.enqueue(item);
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.head.element;
    }

    @Override
    public void push(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            node.next = this.head;
            this.head = node;
        }
        ++this.size;
    }

    @Override
    public E pop() {
        return this.dequeue();
    }

    @Override
    public void enqueue(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) this.head = node;
        else this.tail.next = node;
        this.tail = node;
        ++this.size;
    }

    @Override
    public E dequeue() {
        if (this.isEmpty()) throw new NoSuchElementException();
        E element = this.head.element;
        if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
        } else this.head = this.head.next;
        --this.size;
        return element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedNode<E> next = LinkedList.this.head;

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
