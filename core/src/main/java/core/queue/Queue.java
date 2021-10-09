package core.queue;

import core.deque.Deque;
import core.linkedlist.SinglyLinkedListDoubleReference;

import java.util.function.Supplier;

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

    static <E> Queue<E> fromSinglyLinkedListDoubleReference() {
        final var list = new SinglyLinkedListDoubleReference<E>();
        return new Queue<E>() {
            @Override
            public int size() {
                return list.size();
            }

            @Override
            public boolean isEmpty() {
                return list.isEmpty();
            }

            @Override
            public E peek() {
                return list.peek();
            }

            @Override
            public void enqueue(E element) {
                list.appendTail(element);
            }

            @Override
            public E dequeue() {
                return list.removeHead();
            }
        };
    }
}