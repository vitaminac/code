package collections.set;

import collections.hashtable.HashTable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MutableSet<E>
        extends NavigableSet<E> {
    void add(E element);

    void remove(E element);

    void clear();

    static <T> MutableSet<T> fromHashTable(final Supplier<HashTable<T>> supplier) {
        final HashTable<T> hashTable = supplier.get();
        return new MutableSet<T>() {
            @Override
            public int size() {
                return hashTable.size();
            }

            @Override
            public void enumerate(Consumer<? super T> consumer) {
                for (final var key : hashTable) {
                    consumer.accept(key);
                }
            }

            @Override
            public boolean contains(T element) {
                return hashTable.get(element) != null;
            }

            @Override
            public void add(T element) {
                hashTable.put(element);
            }

            @Override
            public void remove(T element) {
                hashTable.remove(element);
            }

            @Override
            public void clear() {
                hashTable.clear();
            }
        };
    }
}
