package collections.hashtable;

import core.functional.Enumerable;

public interface HashTable<Item>
        extends Enumerable<Item> {
    boolean isEmpty();

    int size();

    Item get(final Item hint);

    void put(final Item item);

    void remove(final Item hint);

    void clear();
}
