package code.adt.dict;

import code.adt.map.Map;

import java.util.function.Consumer;

public interface BinarySearchTree<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    void findRange(Key min, Key max, Consumer<Key> consumer);
}