package core.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionalLockArrayBlockingQueue<E> implements BlockingQueue<E> {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final E[] elements;
    private int size = 0;

    public ConditionalLockArrayBlockingQueue(final int capacity) {
        @SuppressWarnings("unchecked") final var elements = (E[]) new Object[capacity];
        this.elements = elements;
    }


    @Override
    public void enqueue(final E element) throws InterruptedException {
        this.lock.lockInterruptibly();
        try {
            while (this.size == this.elements.length) {
                notFull.await();
            }
            this.elements[this.size++] = element;
            this.notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E dequeue() throws InterruptedException {
        this.lock.lockInterruptibly();
        try {
            while (this.size == 0) {
                notEmpty.await();
            }
            final var result = this.elements[--this.size];
            this.elements[this.size] = null;
            this.notFull.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return this.size;
    }
}
