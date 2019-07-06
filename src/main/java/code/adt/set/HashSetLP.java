package code.adt.set;

import code.adt.map.HashTableMapLP;

public class HashSetLP<E> extends AbstractHashSet<E> {
    protected HashSetLP() {
        super(new HashTableMapLP<>());
    }
}
