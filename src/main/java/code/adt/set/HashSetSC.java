package code.adt.set;

import code.adt.map.HashTableMapSC;

public class HashSetSC<E> extends AbstractHashSet<E> {
    public HashSetSC(int capacity) {
        super(new HashTableMapSC<>(capacity));
    }
}
