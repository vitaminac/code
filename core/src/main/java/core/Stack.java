package core;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Stack<E> extends Enumerable<E> {
    int size();

    boolean isEmpty();

    E peek();

    void push(E element);

    E pop();

    static <E> Stack<E> fromDeque(final Supplier<Deque<E>> supplier) {
        final var deque = supplier.get();
        return new Stack<E>() {
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
            public void push(E element) {
                deque.addFirst(element);
            }

            @Override
            public E pop() {
                return deque.removeFirst();
            }

            @Override
            public void forEach(Consumer<? super E> consumer) {
                deque.forEach(consumer);
            }
        };
    }
}
