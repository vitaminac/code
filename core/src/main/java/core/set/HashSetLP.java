package core.set;

import core.map.OpenAddressingHashTableMapLP;

public class HashSetLP<E> extends AbstractHashSet<E> {
    protected HashSetLP() {
        super(new OpenAddressingHashTableMapLP<>());
    }
}
