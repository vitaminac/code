package collections.set;

import collections.map.MutableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MutableSet<E>
        extends NavigableSet<E> {
    void add(E element);

    void remove(E element);

    void clear();

    static <T> MutableSet<T> fromMap(final Supplier<MutableMap<T, Boolean>> supplier) {
        final MutableMap<T, Boolean> map = supplier.get();
        return new MutableSet<T>() {
            @Override
            public int size() {
                return map.size();
            }

            @Override
            public void enumerate(Consumer<? super T> consumer) {
                for (final var key : map) {
                    consumer.accept(key);
                }
            }

            @Override
            public boolean contains(T element) {
                return map.get(element) != null;
            }

            @Override
            public void add(T element) {
                map.put(element, Boolean.TRUE);
            }

            @Override
            public void remove(T element) {
                map.remove(element);
            }

            @Override
            public void clear() {
                map.clear();
            }
        };
    }
}
