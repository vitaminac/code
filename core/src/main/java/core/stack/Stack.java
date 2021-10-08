package core.stack;

import java.util.function.Supplier;

import core.deque.Deque;
import core.linkedlist.SinglyLinkedListDoubleReference;

public interface Stack<E> {
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
        };
    }

    static <E> Stack<E> fromSinglyLinkedListDoubleReference() {
        final SinglyLinkedListDoubleReference<E> list = new SinglyLinkedListDoubleReference<>();
        return new Stack<E>() {
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
            public void push(E element) {
                list.prependHead(element);
            }

            @Override
            public E pop() {
                return list.removeHead();
            }
        };
    }
}
