package collections.map;

import collections.hashtable.HashTable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MutableMap<Domain, CoDomain>
        extends Map<Domain, CoDomain> {
    void put(final Domain key, final CoDomain value);

    void remove(final Domain key);

    void clear();

    static <Domain, CoDomain> MutableMap<Domain, CoDomain> fromHashTable(final Supplier<HashTable<Domain, CoDomain>> supplier) {
        final HashTable<Domain, CoDomain> hashTable = supplier.get();
        return new MutableMap<Domain, CoDomain>() {
            @Override
            public void put(final Domain key, final CoDomain value) {
                hashTable.put(key, value);
            }

            @Override
            public void remove(final Domain key) {
                hashTable.remove(key);
            }

            @Override
            public void clear() {
                hashTable.clear();
            }

            @Override
            public boolean isEmpty() {
                return hashTable.isEmpty();
            }

            @Override
            public int size() {
                return hashTable.size();
            }

            @Override
            public CoDomain get(final Domain domain) {
                return hashTable.get(domain);
            }

            @Override
            public void enumerate(Consumer<? super Domain> consumer) {
                hashTable.enumerate(consumer);
            }
        };
    }
}
