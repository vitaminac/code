package code.adt.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ResizingArrayQueue<E> implements Queue<E> {
    private E[] queue;
    private int first = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public ResizingArrayQueue() {
        this.queue = (E[]) new Object[2];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public E peek() {
        if (!this.isEmpty())
            return this.queue[this.first];
        else throw new RuntimeException("The queue is empty");
    }

    @Override
    public void enqueue(E element) {
        if (size == queue.length) this.resize(2 * this.queue.length);
        this.queue[(this.first + this.size++) % this.queue.length] = element;
    }

    @Override
    public E dequeue() {
        if (this.isEmpty()) throw new NoSuchElementException("Queue underflow");
        final E item = this.queue[this.first];
        this.first = (this.first + 1) % this.queue.length;
        --this.size;
        if (this.size > 0 && this.size == this.queue.length / 4) resize(this.queue.length / 2);
        return item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int remain = ResizingArrayQueue.this.size;
            private int nextIndex = ResizingArrayQueue.this.first;

            @Override
            public boolean hasNext() {
                return this.remain > 0;
            }

            @Override
            public E next() {
                this.remain--;
                return ResizingArrayQueue.this.queue[this.nextIndex++ % ResizingArrayQueue.this.queue.length];
            }
        };
    }

    // resize the underlying array
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] old = this.queue;
        this.queue = (E[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            this.queue[i] = old[(this.first + i) % old.length];
        }
        this.first = 0;
    }

    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) System.out.print(queue.dequeue() + " ");
        }
        System.out.println("(" + queue.size() + " left on queue)");
    }
}
