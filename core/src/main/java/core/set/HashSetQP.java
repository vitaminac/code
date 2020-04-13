package core.set;

import core.map.OpenAddressingHashTableMapQP;

public class HashSetQP<E> extends AbstractHashSet<E> {
    protected HashSetQP() {
        super(new OpenAddressingHashTableMapQP<>());
    }
}
