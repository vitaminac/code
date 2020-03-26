package code.adt.set;

import code.adt.map.OpenAddressingHashTableMapLP;

public class HashSetLP<E> extends AbstractHashSet<E> {
    protected HashSetLP() {
        super(new OpenAddressingHashTableMapLP<>());
    }
}
