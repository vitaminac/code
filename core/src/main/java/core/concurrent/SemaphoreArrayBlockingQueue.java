package core.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreArrayBlockingQueue<E> implements BlockingQueue<E> {
    private final E[] elements;

    private int head = 0;
    private int tail = 0;

    private final Semaphore empty;
    private final Semaphore full;
    private final Semaphore tailSemaphore = new Semaphore(1);
    private final Semaphore headSemaphore = new Semaphore(1);

    public SemaphoreArrayBlockingQueue(final int capacity) {
        @SuppressWarnings("unchecked") final var elements = (E[]) new Object[capacity];
        this.elements = elements;
        this.empty = new Semaphore(0);
        this.full = new Semaphore(capacity);
    }

    @Override
    public void enqueue(final E element) throws InterruptedException {
        this.full.acquire();

        this.tailSemaphore.acquire();
        this.elements[this.tail] = element;
        this.tail = (this.tail + 1) % this.elements.length;
        this.tailSemaphore.release();

        this.empty.release();
    }

    @Override
    public E dequeue() throws InterruptedException {
        this.empty.acquire();

        this.headSemaphore.acquire();
        final E result = this.elements[this.head];
        this.head = (this.head + 1) % this.elements.length;
        this.headSemaphore.release();

        this.full.release();
        return result;
    }
}
