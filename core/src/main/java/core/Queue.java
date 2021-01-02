package core;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Queue<E> extends Enumerable<E> {
    int size();

    boolean isEmpty();

    E peek();

    void enqueue(E element);

    E dequeue();

    static <E> Queue<E> fromDeque(final Supplier<Deque<E>> supplier) {
        final var deque = supplier.get();
        return new Queue<E>() {
            @Override
            public int size() {
                return deque.size();
            }

            @Override
            public boolean isEmpty() {
                return deque.isEmpty();
            }

            @Override
            public E peek() {
                return deque.getFirst();
            }

            @Override
            public void enqueue(E element) {
                deque.addLast(element);
            }

            @Override
            public E dequeue() {
                return deque.removeFirst();
            }

            @Override
            public void forEach(Consumer<? super E> consumer) {
                deque.forEach(consumer);
            }
        };
    }
}