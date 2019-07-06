package code.adt.set;

import code.adt.map.HashTableMapQP;

public class HashSetQP<E> extends AbstractHashSet<E> {
    protected HashSetQP() {
        super(new HashTableMapQP<>());
    }
}
