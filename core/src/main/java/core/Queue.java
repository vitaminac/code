package core;

import core.linkedlist.SinglyLinkedListDoubleReference;

import java.util.Iterator;
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

    static <E> Queue<E> fromSinglyLinkedListDoubleReference() {
        final SinglyLinkedListDoubleReference<E> list = new SinglyLinkedListDoubleReference<>();
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