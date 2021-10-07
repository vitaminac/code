package core;

import java.util.Iterator;
import java.util.function.Supplier;

// TODO: rename to collection
public interface Bag<E> extends Iterable<E> {
    void add(E element);

    boolean isEmpty();

    int size();

    static <E> Bag<E> fromList(final Supplier<List<E>> listSupplier) {
        final var list = listSupplier.get();
        return new Bag<E>() {
            @Override
            public void add(E element) {
                list.add(list.size(), element);
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
