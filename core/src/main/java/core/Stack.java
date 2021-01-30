package core;

import core.linkedlist.SinglyLinkedListDoubleReference;

import java.util.Iterator;
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

            @Override
            public void forEach(Consumer<? super E> consumer) {
                list.forEach(consumer);
            }

            @Override
            public Iterator<E> iterator() {
                return list.iterator();
            }
        };
    }
}
