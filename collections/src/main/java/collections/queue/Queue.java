package collections.queue;

import java.util.function.Supplier;

import collections.deque.Deque;
import collections.linkedlist.Steque;

public interface Queue<E> {
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
        };
    }

    static <E> Queue<E> fromSteque(final Supplier<Steque<E>> supplier) {
        final var steque = supplier.get();
        return new Queue<E>() {
            @Override
            public int size() {
                return steque.size();
            }

            @Override
            public boolean isEmpty() {
                return steque.isEmpty();
            }

            @Override
            public E peek() {
                return steque.peek();
            }

            @Override
            public void enqueue(E element) {
                steque.append(element);
            }

            @Override
            public E dequeue() {
                return steque.pop();
            }
        };
    }
}