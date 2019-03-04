package code.adt;

public class StreamingAlgorithm<E> {
    private Queue<E> queue;
    private int k;

    public StreamingAlgorithm(int k) {
        this.queue = new ArrayList<>();
        this.k = k;
    }

    public void add(E element) {
        if (this.queue.size() >= this.k) {
            this.queue.dequeue();
        }
        this.queue.enqueue(element);
    }

    public Iterable<E> getLastKItems() {
        return this.queue;
    }
}
