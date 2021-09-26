package core;

import core.map.Map;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Bag<E> extends Collection<E> {
    void add(E element);

    void add(E element, int nCopies);

    void remove(E element);

    void remove(E element, int nCopies);

    void clear();

    int getCount(E element);

    static <T> Bag<T> fromMap(Supplier<Map<T, Integer>> supplier) {
        return new Bag<T>() {
            private final Map<T, Integer> map = supplier.get();
            private int size = 0;

            @Override
            public int size() {
                return this.size;
            }

            @Override
            public void forEach(Consumer<? super T> consumer) {
                map.forEach(consumer);
            }

            @Override
            public int getCount(T element) {
                return map.get(element);
            }

            @Override
            public void add(T element) {
                this.add(element, 1);
            }

            @Override
            public void add(T element, int nCopies) {
                final var copiesCount = map.get(element);
                if (copiesCount == null) {
                    map.put(element, nCopies);
                } else {
                    map.put(element, copiesCount + nCopies);
                }
                this.size += nCopies;
            }

            @Override
            public void remove(T element) {
                this.remove(element, 1);
            }

            @Override
            public void remove(T element, int nCopies) {
                final var copiesCount = map.get(element);
                if (copiesCount == null) {
                    throw new NoSuchElementException();
                }
                if (copiesCount < nCopies) {
                    throw new IllegalArgumentException(
                            String.format("trying to remove %s %s but there are only %s copies of %s in the bag",
                                    nCopies, element, copiesCount, element));
                }
                final int newCopiesCount = copiesCount - nCopies;
                if (newCopiesCount == 0) {
                    map.remove(element);
                } else {
                    map.put(element, newCopiesCount);
                }
                this.size -= nCopies;
            }

            @Override
            public void clear() {
                map.clear();
            }
        };
    }
}
