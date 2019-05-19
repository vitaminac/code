package code.adt.dict;

import code.adt.Relation;
import code.adt.map.Map;

public interface BinarySearchTree<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    Iterable<Relation<Key, Value>> findRange(Key min, Key max);
}
