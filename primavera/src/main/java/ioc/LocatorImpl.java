package ioc;

import ioc.error.DefinitionNotFound;
import ioc.error.DuplicateDefinitionException;
import ioc.provider.Provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocatorImpl implements Locator {
    private final Map<Object, Provider<?>> map = new ConcurrentHashMap<>();

    @Override
    public <T> T find(Class<T> type) {
        return this.findByKey(type);
    }

    @Override
    public <T> T find(String name) {
        return this.findByKey(name);
    }

    @Override
    public <T> void register(String name, Provider<T> provider) {
        if (!this.map.containsKey(name)) {
            this.map.put(name, provider);
        } else {
            throw new DuplicateDefinitionException(name);
        }
    }

    @Override
    public <T> void register(Class<? super T> type, Provider<T> provider) {
        if (type.isInterface()) {
            this.map.put(type, provider);
        }
        for (Class<?> i : type.getInterfaces()) {
            this.map.put(i, provider);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T findByKey(Object key) {
        final Provider<?> provider = this.map.get(key);
        if (provider == null) {
            throw new DefinitionNotFound(key);
        } else {
            return (T) provider.provide();
        }
    }
}
