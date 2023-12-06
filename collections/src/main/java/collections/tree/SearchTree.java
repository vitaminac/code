package collections.tree;

import core.functional.Enumerable;

public interface SearchTree<Key extends Comparable<? super Key>, Value> extends Enumerable<Key> {
    boolean isEmpty();

    int size();

    Value search(Key key);

    void insert(Key key, Value value);

    void remove(Key key);
}
