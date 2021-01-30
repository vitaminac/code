package core.tree;

import core.Collection;

public interface SearchTree<Key extends Comparable<? super Key>, Value>
        extends Collection<Key> {
    boolean isEmpty();

    Value search(Key key);

    void insert(Key key, Value value);

    void remove(Key key);

    int size();
}
