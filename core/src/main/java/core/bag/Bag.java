package core.bag;

import java.util.Iterator;
import java.util.function.Supplier;

import core.behaviour.Collection;
import core.deque.Deque;
import core.linkedlist.SinglyLinkedListDoubleReference;

public interface Bag<E> extends Collection<E> {
    void add(E element);

    static <E> Bag<E> fromDeque(final Supplier<Deque<E>> supplier) {
        final var deque = supplier.get();
        return new Bag<E>() {
            @Override
            public void add(final E element) {
                deque.addLast(element);
            }

            @Override
            public boolean isEmpty() {
                return deque.isEmpty();
            }

            @Override
            public int size() {
                return deque.size();
            }

            @Override
            public Iterator<E> iterator() {
                return deque.iterator();
            }
        };
    }

    static <E> Bag<E> fromSinglyLinkedListDoubleReference() {
        final var list = new SinglyLinkedListDoubleReference<E>();
        return new Bag<E>() {
            @Override
            public void add(E element) {
                list.prependHead(element);
            }

            @Override
            public boolean isEmpty() {
                return list.isEmpty();
            }

            @Override
            public int size() {
                return list.size();
            }

            @Override
            public Iterator<E> iterator() {
                return list.iterator();
            }
        };
    }
}
