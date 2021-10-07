package core.set;

import java.util.function.Consumer;
import java.util.function.Supplier;

import core.map.Map;

public interface MutableSet<E> extends NavigableSet<E> {
    void add(E element);

    void remove(E element);

    void clear();

    static <T> MutableSet<T> fromMap(Supplier<Map<T, Boolean>> supplier) {
        final Map<T, Boolean> map = supplier.get();
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
