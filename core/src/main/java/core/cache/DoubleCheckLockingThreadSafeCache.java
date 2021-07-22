package core.cache;

import java.util.function.Supplier;

public class DoubleCheckLockingThreadSafeCache<E> implements Cache<E> {
    private final Supplier<? extends E> supplier;
    private E reference;

    public DoubleCheckLockingThreadSafeCache(Supplier<? extends E> supplier) {
        this.supplier = supplier;
    }

    public E getValue() {
        if (this.reference == null) {
            synchronized (this) {
                if (this.reference == null) {
                    this.reference = supplier.get();
                }
            }
        }
        return this.reference;
    }
}
