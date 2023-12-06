package collections.list;

import core.behaviour.OrderedCollection;
import core.behaviour.RandomAccess;

import java.util.Iterator;

public interface List<E> extends OrderedCollection<E>, RandomAccess<E> {
    static <E> List<E> fromArray(final E[] elements) {
        return new List<>() {
            @Override
            public int size() {
                return elements.length;
            }

            @Override
            public boolean isEmpty() {
                return elements.length == 0;
            }

            @Override
            public void clear() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(int index, E element) {
                throw new UnsupportedOperationException();
            }

            @Override
            public E remove(int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int indexOf(E element) {
                for (int i = 0; i < elements.length; i++) {
                    if (elements[i].equals(element)) {
                        return i;
                    }
                }
                return -1;
            }

            @Override
            public int lastIndexOf(E element) {
                for (int i = elements.length - 1; i >= 0; i--) {
                    if (elements[i].equals(element)) {
                        return i;
                    }
                }
                return -1;
            }

            @Override
            public E get(int index) {
                return elements[index];
            }

            @Override
            public E set(int index, E element) {
                throw new RuntimeException();
            }

            @Override
            public Iterator<E> iterator() {
                return java.util.Arrays.stream(elements).iterator();
            }
        };
    }

    void clear();

    // TODO: rename to insert
    void add(int index, E element);

    E remove(int index);

    int indexOf(E element);

    int lastIndexOf(E element);

    default void swap(int i, int j) {
        this.set(i, this.set(j, this.get(i)));
    }
}
