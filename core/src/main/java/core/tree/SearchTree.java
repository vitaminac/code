package core.tree;

import core.Enumerable;

public interface SearchTree<Key extends Comparable<? super Key>, Value> extends Enumerable<Key> {
    boolean isEmpty();

    Value search(Key key);

    void insert(Key key, Value value);

    void remove(Key key);
}
