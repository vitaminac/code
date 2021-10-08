package core.queue;

import java.util.Comparator;
import java.util.function.Supplier;

import core.deque.Deque;

public class MinQueue<E> implements Queue<E> {
    private final Comparator<? super E> comparator;
    private final Deque<E> minDeque;
    private final Queue<E> queue;

    public MinQueue(Supplier<Deque<E>> factory, Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.queue = Queue.fromDeque(factory);
        this.minDeque = factory.get();
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public E peek() {
        return this.queue.peek();
    }

    public E min() {
        return this.minDeque.getFirst();
    }

    @Override
    public void enqueue(E element) {
        this.queue.enqueue(element);
        while (!this.minDeque.isEmpty() && this.comparator.compare(element, this.minDeque.getLast()) < 0)
            this.minDeque.removeLast();
        this.minDeque.addLast(element);
    }

    @Override
    public E dequeue() {
        final E tmp = this.queue.dequeue();
        if (tmp == this.minDeque.getFirst()) this.minDeque.removeFirst();
        return tmp;
    }
}
