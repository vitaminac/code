package code.adt.set;

import code.adt.map.OpenAddressingHashTableMapQP;

public class HashSetQP<E> extends AbstractHashSet<E> {
    protected HashSetQP() {
        super(new OpenAddressingHashTableMapQP<>());
    }
}
