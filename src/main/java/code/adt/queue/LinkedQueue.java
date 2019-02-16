package code.adt.queue;

import code.adt.LinkedNode;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LinkedQueue<E> implements Queue<E> {

    private LinkedNode<E> head = null;
    private LinkedNode<E> tail = null;
    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new RuntimeException("The Queue is empty");
        return this.head.element;
    }

    @Override
    public void enqueue(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) this.head = node;
        else this.tail.next = node;
        this.tail = node;
        this.size++;
    }

    @Override
    public E dequeue() {
        if (this.isEmpty()) throw new NoSuchElementException("Queue underflow");
        E item = this.head.element;
        this.head = this.head.next;
        this.size--;
        return item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedNode<E> current = LinkedQueue.this.head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E item = current.element;
                current = current.next;
                return item;
            }
        };
    }

    public static void main(String[] args) {
        Queue<String> queue = new LinkedQueue<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                System.out.print(queue.dequeue() + " ");
        }
        System.out.println("(" + queue.size() + " left on queue)");
    }
}
