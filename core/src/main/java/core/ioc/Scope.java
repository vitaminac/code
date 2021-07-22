package core.ioc;

import java.util.function.Supplier;

import core.cache.AtomicReferenceThreadSafeCachedValue;

public enum Scope {
    SINGLETON {
        @Override
        public <T> Provider<T> getProvider(final Supplier<T> factory) {
            return new Provider<>() {
                private final AtomicReferenceThreadSafeCachedValue<T> cache = new AtomicReferenceThreadSafeCachedValue<>(factory::get);

                @Override
                public T provide() {
                    return this.cache.getValue();
                }
            };
        }
    },
    PROTOTYPE {
        @Override
        public <T> Provider<T> getProvider(final Supplier<T> factory) {
            return factory::get;
        }
    },
    THREAD_LOCAL {
        @Override
        public <T> Provider<T> getProvider(final Supplier<T> factory) {
            return new Provider<T>() {
                private final ThreadLocal<T> threadLocal = ThreadLocal.withInitial(factory::get);

                @Override
                public T provide() {
                    return threadLocal.get();
                }
            };
        }
    };

    public abstract <T> Provider<T> getProvider(final Supplier<T> factory);
}
