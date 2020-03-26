package code.adt.set;

import code.adt.map.OpenAddressingHashTableMapDH;

public class HashSetDH<E> extends AbstractHashSet<E> {
    protected HashSetDH() {
        super(new OpenAddressingHashTableMapDH<>());
    }
}
