import provider.Provider;
import provider.SingletonProvider;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    // TODO: Lazy load
    // TODO: ApplicationEventPublisher
    // TODO: ResourcePatternResolver
    // TODO: Injector parent - Injector Hierarchy
    // TODO: scope: per request, per session, per application
    // TODO: inheritance, class A extends B, get(B.clcass) should also be prosible to return instance A
    // TODO: allow interface Map<Interface<?>, Provider<?>>
    private final Map<Class<?>, Provider<?>> providerMap = new HashMap<>();

    public ApplicationContext() {
        try {
            this.addProvider(ApplicationContext.class, new SingletonProvider<>(this));
        } catch (DuplicateDefinitionException e) {
            // never
        }
    }

    <T> T get(Class<T> type) throws ServiceNotFound {
        final Provider<?> provider = this.providerMap.get(type);
        if (provider == null) {
            throw new ServiceNotFound(type);
        } else {
            return (T) provider.provide();
        }
    }

    <T> void addProvider(Class<T> type, Provider<T> provider) throws DuplicateDefinitionException {
        if (!this.providerMap.containsKey(type)) {
            this.providerMap.put(type, provider);
        } else {
            throw new DuplicateDefinitionException(type);
        }
    }
}
