package core.concurrent;

public interface BlockingQueue<E> {
    void enqueue(E element) throws InterruptedException;

    E dequeue() throws InterruptedException;
}
