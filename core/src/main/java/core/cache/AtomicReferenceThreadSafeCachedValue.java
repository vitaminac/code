package core.cache;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class AtomicReferenceThreadSafeCachedValue<E> implements Cache<E> {
    private final Supplier<? extends E> supplier;
    private final AtomicReference<E> reference = new AtomicReference<>(null);

    public AtomicReferenceThreadSafeCachedValue(Supplier<? extends E> supplier) {
        this.supplier = supplier;
    }

    public E getValue() {
        if (reference.get() == null) reference.compareAndSet(null, supplier.get());
        return this.reference.get();
    }
}
