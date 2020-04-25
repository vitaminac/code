package core.concurrent;

public interface BlockingQueue<E> {
    void enqueue(E e) throws InterruptedException;

    E dequeue() throws InterruptedException;
}
