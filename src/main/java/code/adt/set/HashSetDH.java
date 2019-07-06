package code.adt.set;

import code.adt.map.HashTableMapDH;

public class HashSetDH<E> extends AbstractHashSet<E> {
    protected HashSetDH() {
        super(new HashTableMapDH<>());
    }
}
