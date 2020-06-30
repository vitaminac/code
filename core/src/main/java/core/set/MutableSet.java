package core.set;

import core.map.Map;

import java.util.function.Supplier;

public interface MutableSet<E> extends Set<E> {
    void add(E element);

    void remove(E element);

    void clear();

    default MutableSet<E> fromMap(Supplier<Map<E, Boolean>> supplier) {
        final Map<E, Boolean> map = supplier.get();
        return new MutableSet<E>() {
            @Override
            public boolean contains(E element) {
                return map.map(element) != null;
            }

            @Override
            public void add(E element) {
                map.link(element, Boolean.TRUE);
            }

            @Override
            public void remove(E element) {
                map.remove(element);
            }

            @Override
            public void clear() {
                map.clear();
            }
        };
    }
}
