package code.adt.set;

import code.adt.map.SeparateChainingHashTableMap;

public class HashSetSC<E> extends AbstractHashSet<E> {
    public HashSetSC() {
        super(new SeparateChainingHashTableMap<>());
    }
}
