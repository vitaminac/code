import injection.Injectable;
import injection.Scope;
import provider.DefaultTypeFactory;
import provider.PrototypeProvider;
import provider.Provider;
import provider.SingletonProvider;
import provider.TypeFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    public ApplicationContext(Class... types) {
        try {
            this.addProvider(ApplicationContext.class, new SingletonProvider<>(() -> this));
            for (Class type : types) {
                Injectable injectable = (Injectable) type.getAnnotation(Injectable.class);
                Object instance;
                if (injectable != null) {
                    this.addProviderByConstructor(type, injectable.scope());
                    instance = this.get(type);
                } else {
                    instance = type.newInstance();
                }
                for (Method method : type.getDeclaredMethods()) {
                    injectable = method.getAnnotation(Injectable.class);
                    if (injectable != null) {
                        method.setAccessible(true);
                        this.addProviderByFactoryMethod(method.getReturnType(), method, injectable.scope(), instance);
                    }

                }
            }
        } catch (DuplicateDefinitionException | IllegalAccessException | InstantiationException e) {
            throw new LoadDefinitionException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
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

    private <T> void addProviderByFactoryMethod(Class<T> returnType, Method method, Scope scope, Object instance) {
        // TODO: satisfy method dependencies
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
        this.addProviderByFactory(returnType, factory, scope);
    }

    private <T> void addProviderByConstructor(Class<T> type, Scope scope) {
        // TODO: dependencies graph
        Arrays.stream(type.getConstructors())
                .filter((constructor) -> Arrays.stream(constructor.getParameterTypes())
                        .allMatch((arg) -> this.get(arg) != null))
                .findFirst()
                .map((constructor -> (TypeFactory<T>) () -> {
                    try {
                        return (T) constructor.newInstance(Arrays.stream(constructor.getParameterTypes()).map(ApplicationContext.this::get).toArray());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new LoadDefinitionException(e);
                    }
                }))
                .ifPresent((factory -> this.addProviderByFactory(type, factory, scope)));
    }

    private <T> void addProviderByFactory(Class<T> type, TypeFactory<T> factory, Scope scope) {
        switch (scope) {
            case Singleton:
                this.addProvider(type, new SingletonProvider<T>(factory));
                break;
            case Prototype:
                this.addProvider(type, new PrototypeProvider<>(factory));
                break;
        }
    }
}
