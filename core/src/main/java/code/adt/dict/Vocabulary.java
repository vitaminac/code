package code.adt.dict;

import code.adt.map.Relation;

public class Vocabulary<Key extends Comparable<Key>, Value> extends Relation<Key, Value> implements Comparable<Key> {
    public Vocabulary(Key key, Value value) {
        super(key, value);
    }

    @Override
    public int compareTo(Key key) {
        return this.getKey().compareTo(key);
    }
}
