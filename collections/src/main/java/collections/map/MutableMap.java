package collections.map;

import collections.hashtable.HashTable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MutableMap<Domain, CoDomain>
        extends Map<Domain, CoDomain> {
    void put(final Domain domain, final CoDomain coDomain);

    void remove(final Domain domain);

    void clear();

    static <Domain, CoDomain> MutableMap<Domain, CoDomain> fromHashTable(final Supplier<HashTable<Relation<Domain, CoDomain>>> supplier) {
        final HashTable<Relation<Domain, CoDomain>> hashTable = supplier.get();
        return new MutableMap<>() {
            @Override
            public void put(final Domain domain, final CoDomain coDomain) {
                hashTable.put(new Relation<>(domain, coDomain));
            }

            @Override
            public void remove(final Domain domain) {
                hashTable.remove(new Relation<>(domain, null));
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
                final var relation = hashTable.get(new Relation<>(domain, null));
                return relation == null ? null : relation.getValue();
            }

            @Override
            public void enumerate(Consumer<? super Domain> consumer) {
                hashTable.enumerate((relation) -> consumer.accept(relation.getKey()));
            }
        };
    }
}
