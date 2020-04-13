package core;

public interface Deque<E> extends Queue<E>, Stack<E> {
    int size();

    void addFirst(E element);

    void addLast(E element);

    void removeFirst();

    void removeLast();

    E first();

    E last();

    boolean isEmpty();

    @Override
    default E head() {
        return this.first();
    }

    @Override
    default E top() {
        return this.first();
    }

    @Override
    default void push(E element) {
        this.addFirst(element);
    }

    @Override
    default E pop() {
        E ret = this.first();
        this.removeFirst();
        return ret;
    }

    @Override
    default void enqueue(E element) {
        this.addLast(element);
    }

    @Override
    default E dequeue() {
        E ret = this.first();
        this.removeFirst();
        return ret;
    }
}
