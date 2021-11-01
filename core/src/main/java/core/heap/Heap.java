package core.heap;

public interface Heap<E> {
    int size();

    boolean isEmpty();

    E min();

    void add(E element);

    E removeMin();
}
