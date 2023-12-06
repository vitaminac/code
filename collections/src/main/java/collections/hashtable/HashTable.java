package collections.hashtable;

import core.functional.Enumerable;

public interface HashTable<Key, Value>
        extends Enumerable<Key> {
    boolean isEmpty();

    int size();

    Value get(final Key key);

    void put(final Key key, final Value value);

    void remove(final Key key);

    void clear();
}
