package core.set;

import core.map.SeparateChainingHashTableMap;

public class HashSetSC<E> extends AbstractHashSet<E> {
    public HashSetSC() {
        super(new SeparateChainingHashTableMap<>());
    }
}
