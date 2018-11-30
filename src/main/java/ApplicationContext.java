import error.DefinitionNotFound;
import error.DuplicateDefinitionException;
import error.LoadDefinitionException;
import injection.ContextConfig;
import injection.Injectable;
import injection.Scope;
import org.reflections.Reflections;
import provider.DefaultTypeFactory;
import provider.PrototypeProvider;
import provider.Provider;
import provider.SingletonProvider;
import provider.ThreadLocalProvider;
import provider.TypeFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    // TODO: Lazy load
    // TODO: ApplicationEventPublisher
    // TODO: ResourcePatternResolver
    // TODO: Injector parent - Injector Hierarchy
    // TODO: scope: per request, per session, per application
    // TODO: inheritance, class A extends B, get(B.clcass) should also be prosible to return instance A
    // TODO: allow interface Map<Interface<?>, Provider<?>>
    // TODO: bean with name
    private final Map<Object, Provider<?>> providerMap = new ConcurrentHashMap<>();

    private ApplicationContext() {
        this.addProvider(ApplicationContext.class, new SingletonProvider<>(() -> this));
    }

    public <T extends ContextConfig> void registerConfig(Class<T> config) {
        try {
            final T instance = config.newInstance();
            for (Method method : config.getDeclaredMethods()) {
                Injectable injectable = method.getAnnotation(Injectable.class);
                if (injectable != null) {
                    method.setAccessible(true);
                    this.addProviderByFactoryMethod(method.getReturnType(), method, injectable.scope(), instance);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LoadDefinitionException(e);
        }
    }

    public void registerInjectable(Class... types) {
        try {
            for (Class type : types) {
                Injectable injectable = (Injectable) type.getAnnotation(Injectable.class);
                if (injectable != null) {
                    this.addProviderByConstructor(type, injectable.scope());
                }
            }
        } catch (DuplicateDefinitionException e) {
            throw new LoadDefinitionException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        return (T) this.getByKey(type);
    }

    public Object getByKey(Object key) {
        final Provider<?> provider = this.providerMap.get(key);
        if (provider == null) {
            throw new DefinitionNotFound(key);
        } else {
            return provider.provide();
        }
    }

    public <T> void addProvider(Object key, Provider<T> provider) {
        if (!this.providerMap.containsKey(key)) {
            this.providerMap.put(key, provider);
        } else {
            throw new DuplicateDefinitionException(key);
        }
    }

    public <T> void addProvider(Class<T> type, Provider<T> provider) {
        if (type.isInterface()) {
            this.addProvider(type, provider);
        }
        for (Class<?> i : type.getInterfaces()) {
            this.addProvider(i, provider);
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
                    throw new LoadDefinitionException(e);
                }
            }
        };
        this.addProviderByFactory(returnType, factory, scope);
    }

    private <T> void addProviderByConstructor(Class<T> type, Scope scope) {
        // TODO: dependencies graph
        Arrays.stream(type.getConstructors())
                .filter((constructor) -> Arrays.stream(constructor.getParameterTypes())
                        .allMatch((argType) -> {
                            try {
                                this.get(argType);
                                return true;
                            } catch (DefinitionNotFound e) {
                                return false;
                            }
                        }))
                .findFirst()
                .map((constructor -> (TypeFactory<T>) () -> {
                    try {
                        return (T) constructor.newInstance(Arrays
                                .stream(constructor.getParameterTypes())
                                .map(ApplicationContext.this::get)
                                .toArray());
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
            case Thread:
                this.addProvider(type, new ThreadLocalProvider<T>(factory));
        }
    }

    private static ApplicationContext APPLICATION_CONTEXT = null;

    public static ApplicationContext load() {
        if (APPLICATION_CONTEXT == null) {
            Reflections reflections = new Reflections("");
            Set<Class<? extends ContextConfig>> configs = reflections.getSubTypesOf(ContextConfig.class);
            APPLICATION_CONTEXT = new ApplicationContext();
            for (Class<? extends ContextConfig> config : configs) {
                APPLICATION_CONTEXT.registerConfig(config);
            }
            Set<Class<?>> injectables = reflections.getTypesAnnotatedWith(Injectable.class);
            APPLICATION_CONTEXT.registerInjectable(injectables.toArray(new Class[0]));
        }
        return APPLICATION_CONTEXT;
    }
}
