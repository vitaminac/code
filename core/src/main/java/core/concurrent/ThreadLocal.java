package core.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * TODO: weak reference - prevent memory leak
 * @param <E>
 */
public class ThreadLocal<E> {
    // TODO: change to my implementation
    private final ConcurrentMap<Long, E> map = new ConcurrentHashMap<>();
    private final Supplier<E> supplier;

    public ThreadLocal(Supplier<E> supplier) {
        this.supplier = supplier;
    }

    public E get() {
        final var threadId = Thread.currentThread().getId();
        return map.computeIfAbsent(threadId, (id) -> supplier.get());
    }
}
