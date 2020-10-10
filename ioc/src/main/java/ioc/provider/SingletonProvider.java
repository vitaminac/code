package ioc.provider;

import core.AtomicReferenceThreadSafeCachedValue;

public class SingletonProvider<T> implements Provider<T> {
    private final AtomicReferenceThreadSafeCachedValue<T> cache;

    public SingletonProvider(final Factory<T> factory) {
        this.cache = new AtomicReferenceThreadSafeCachedValue<>(factory::build);
    }

    @Override
    public T provide() {
        return this.cache.getValue();
    }
}
