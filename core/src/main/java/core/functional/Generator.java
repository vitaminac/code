package core.functional;

import java.util.Iterator;
import java.util.NoSuchElementException;

import core.concurrent.BlockingQueue;
import core.concurrent.ConditionalLockArrayBlockingQueue;

public abstract class Generator<E> implements Iterator<E> {
    private static final Object STOP_SIGNAL = new Object();

    private final Thread producer;
    private final BlockingQueue<E> blockingQueue = new ConditionalLockArrayBlockingQueue<>(1);
    private E next;

    public Generator() {
        this.producer = new Thread(() -> {
            Generator.this.generate();
            try {
                Generator.this.blockingQueue.enqueue((E) STOP_SIGNAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        this.producer.start();
    }

    public abstract void generate();

    protected void yieldValue(final E element) {
        try {
            this.blockingQueue.enqueue(element);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        if (this.next == null) {
            try {
                this.next = blockingQueue.dequeue();
            } catch (final InterruptedException e) {
                this.next = (E) STOP_SIGNAL;
            }
        }
        return this.next != STOP_SIGNAL;
    }

    @Override
    public E next() {
        if (!this.hasNext()) throw new NoSuchElementException();
        final var result = this.next;
        this.next = null;
        return result;
    }
}
