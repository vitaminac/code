package core.set;

import core.Enumerable;
import core.map.Map;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Bag<E> extends Enumerable<E> {
    void add(E element);

    void remove(E element);

    void clear();

    int getCount(E element);

    static <T> Bag<T> fromMap(Supplier<Map<T, Integer>> supplier) {
        return new Bag<T>() {
            final Map<T, Integer> map = supplier.get();

            @Override
            public void forEach(Consumer<? super T> consumer) {
                map.forEach(consumer);
            }

            @Override
            public int getCount(T element) {
                return map.map(element);
            }

            @Override
            public void add(T element) {
                Integer n = map.map(element);
                if (n == null) {
                    map.link(element, 1);
                } else {
                    map.link(element, n + 1);
                }
            }

            @Override
            public void remove(T element) {
                Integer n = map.map(element);
                if (n != null) {
                    if (n == 0) {
                        map.remove(element);
                    } else {
                        map.link(element, n - 1);
                    }
                }
            }

            @Override
            public void clear() {
                map.clear();
            }
        };
    }
}