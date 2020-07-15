package core.set;

import core.Enumerable;
import core.map.Map;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface MutableSet<E> extends Set<E>, Enumerable<E> {
    void add(E element);

    void remove(E element);

    void clear();

    default boolean match(Predicate<E> predicate) {
        for (var e : this) {
            if (predicate.test(e)) {
                return true;
            }
        }
        return false;
    }

    static <T> MutableSet<T> fromMap(Supplier<Map<T, Boolean>> supplier) {
        final Map<T, Boolean> map = supplier.get();
        return new MutableSet<T>() {
            @Override
            public void forEach(Consumer<? super T> consumer) {
                map.forEach(consumer);
            }

            @Override
            public boolean contains(T element) {
                return map.map(element) != null;
            }

            @Override
            public void add(T element) {
                map.link(element, Boolean.TRUE);
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
