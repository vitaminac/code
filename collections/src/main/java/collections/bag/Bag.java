package collections.bag;

import java.util.Iterator;
import java.util.function.Supplier;

import core.behaviour.Collection;
import collections.deque.Deque;
import collections.linkedlist.Steque;

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

    static <E> Bag<E> fromSteque(final Supplier<Steque<E>> supplier) {
        final var steque = supplier.get();
        return new Bag<E>() {
            @Override
            public void add(E element) {
                steque.push(element);
            }

            @Override
            public boolean isEmpty() {
                return steque.isEmpty();
            }

            @Override
            public int size() {
                return steque.size();
            }

            @Override
            public Iterator<E> iterator() {
                return steque.iterator();
            }
        };
    }
}
