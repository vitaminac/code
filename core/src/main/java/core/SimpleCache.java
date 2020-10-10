package core;

import java.util.function.Supplier;

public class SimpleCache<E> implements Cache<E> {
    private final Supplier<? extends E> supplier;
    private E reference;

    public SimpleCache(Supplier<? extends E> supplier) {
        this.supplier = supplier;
    }

    public E getValue() {
        if (reference == null) reference = supplier.get();
        return this.reference;
    }
}
