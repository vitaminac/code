package ioc.injection;

import core.AtomicReferenceThreadSafeCachedValue;
import ioc.provider.Factory;
import ioc.provider.Provider;

public enum Scope {
    SINGLETON {
        @Override
        public <T> Provider<T> getProvider(final Factory<T> factory) {
            return new Provider<T>() {
                private final AtomicReferenceThreadSafeCachedValue<T> cache = new AtomicReferenceThreadSafeCachedValue<>(factory::build);

                @Override
                public T provide() {
                    return this.cache.getValue();
                }
            };
        }
    },
    PROTOTYPE {
        @Override
        public <T> Provider<T> getProvider(final Factory<T> factory) {
            return factory::build;
        }
    },
    THREAD_LOCAL {
        @Override
        public <T> Provider<T> getProvider(final Factory<T> factory) {
            return new Provider<T>() {
                private final ThreadLocal<T> threadLocal = ThreadLocal.withInitial(factory::build);

                @Override
                public T provide() {
                    return threadLocal.get();
                }
            };
        }
    };

    public abstract <T> Provider<T> getProvider(final Factory<T> factory);
}
