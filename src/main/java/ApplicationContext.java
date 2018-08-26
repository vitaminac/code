import injection.Injectable;
import injection.Scope;
import provider.DefaultTypeFactory;
import provider.PrototypeProvider;
import provider.Provider;
import provider.SingletonProvider;
import provider.TypeFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    // TODO: Lazy load
    // TODO: ApplicationEventPublisher
    // TODO: ResourcePatternResolver
    // TODO: Injector parent - Injector Hierarchy
    // TODO: scope: per request, per session, per application
    // TODO: inheritance, class A extends B, get(B.clcass) should also be prosible to return instance A
    // TODO: allow interface Map<Interface<?>, Provider<?>>
    private final Map<Class<?>, Provider<?>> providerMap = new ConcurrentHashMap<>();

    public ApplicationContext(Class... configs) {
        try {
            this.addProvider(ApplicationContext.class, new SingletonProvider<>(() -> this));
            for (Class config : configs) {
                final Object instance = config.newInstance();
                for (Method method : config.getDeclaredMethods()) {
                    final Injectable injectable = method.getAnnotation(Injectable.class);
                    if (injectable != null) {
                        method.setAccessible(true);
                        this.addProviderByReflection(method.getReturnType(), method, injectable.scope(), instance);
                    }

                }
            }
        } catch (DuplicateDefinitionException | IllegalAccessException | InstantiationException e) {
            throw new LoadDefinitionException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) throws DefinitionNotFound {
        final Provider<?> provider = this.providerMap.get(type);
        if (provider == null) {
            throw new DefinitionNotFound(type);
        } else {
            return (T) provider.provide();
        }
    }

    public <T> void addProvider(Class<T> type, Provider<T> provider) {
        if (!this.providerMap.containsKey(type)) {
            this.providerMap.put(type, provider);
        } else {
            throw new DuplicateDefinitionException(type);
        }
    }

    public <T> void addType(Class<T> type) {
        this.addProvider(type, new SingletonProvider<>(new DefaultTypeFactory<>(type)));
    }

    private <T> void addProviderByReflection(Class<T> returnType, Method method, Scope scope, Object instance) {
        TypeFactory<T> factory = new TypeFactory<T>() {
            @Override
            @SuppressWarnings("unchecked")
            public T build() {
                try {
                    return (T) method.invoke(instance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return null;
                }
            }
        };
        switch (scope) {
            case Singleton:
                this.addProvider(returnType, new SingletonProvider<T>(factory));
                break;
            case Prototype:
                this.addProvider(returnType, new PrototypeProvider<>(factory));
                break;
        }
    }
}
