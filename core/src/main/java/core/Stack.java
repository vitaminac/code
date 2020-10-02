package core;

import java.util.function.Consumer;

public interface Stack<E> extends Enumerable<E> {
    int size();

    boolean isEmpty();

    E peek();

    void push(E element);

    E pop();

    static <E> Stack<E> fromDeque(final Deque<E> deque) {
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
