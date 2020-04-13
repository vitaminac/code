package core.set;

import core.map.OpenAddressingHashTableMapDH;

public class HashSetDH<E> extends AbstractHashSet<E> {
    protected HashSetDH() {
        super(new OpenAddressingHashTableMapDH<>());
    }
}
