import error.DefinitionNotFound;
import error.DuplicateDefinitionException;
import error.LoadDefinitionException;
import injection.ContextConfig;
import injection.Dependency;
import org.reflections.Reflections;
import provider.Factory;
import provider.MethodFactory;
import provider.PrototypeProvider;
import provider.Provider;
import provider.SingletonProvider;
import provider.ThreadLocalProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext implements Context {
    private static ApplicationContext APPLICATION_CONTEXT = null;

    public static ApplicationContext load() {
        if (APPLICATION_CONTEXT == null) {
            Reflections reflections = new Reflections("");
            Set<Class<? extends ContextConfig>> configs = reflections.getSubTypesOf(ContextConfig.class);
            APPLICATION_CONTEXT = new ApplicationContext();
            Set<Class<?>> dependencies = reflections.getTypesAnnotatedWith(Dependency.class);
            APPLICATION_CONTEXT.registerDependencies(dependencies.toArray(new Class[0]));
            for (Class<? extends ContextConfig> config : configs) {
                APPLICATION_CONTEXT.registerConfig(config);
            }
        }
        return APPLICATION_CONTEXT;
    }

    // TODO: Lazy load
    // TODO: ApplicationEventPublisher
    // TODO: ResourcePatternResolver
    // TODO: Injector parent - Injector Hierarchy
    // TODO: scope: per request, per session, per application
    // TODO: inheritance, class A extends B, getDependency(B.clcass) should also be prosible to return instance A
    // TODO: allow interface Map<Interface<?>, Provider<?>>
    // TODO: bean with name
    // TODO: run with init method of config file
    private final Map<Object, Provider<?>> providerMap = new ConcurrentHashMap<>();

    private ApplicationContext() {
        this.registerProvider(ApplicationContext.class, new SingletonProvider<>(() -> this));
    }

    @Override
    public <T extends ContextConfig> void registerConfig(Class<T> config) {
        try {
            final T instance = config.newInstance();
            for (Method method : config.getDeclaredMethods()) {
                Dependency annotation = method.getAnnotation(Dependency.class);
                if (annotation != null) {
                    method.setAccessible(true);
                    this.addProviderByFactoryMethod(method.getReturnType(), method, instance, annotation);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LoadDefinitionException(e);
        }
    }

    @Override
    public void registerDependencies(Class... types) {
        for (Class<?> type : types) {
            Dependency annotation = type.getAnnotation(Dependency.class);
            if (annotation != null) {
                this.addProviderByConstructor(type, annotation);
            }
        }
    }

    @Override
    public <T> T getDependency(Class<T> type) {
        return this.getByKey(type);
    }


    @Override
    public <T> T getDependencyByName(String name) {
        return this.getByKey(name);
    }

    @Override
    public <T> void registerProvider(String name, Provider<T> provider) {
        if (!this.providerMap.containsKey(name)) {
            this.providerMap.put(name, provider);
        } else {
            throw new DuplicateDefinitionException(name);
        }
    }

    @Override
    public <T> void registerProvider(Class<? super T> type, Provider<T> provider) {
        if (type.isInterface()) {
            this.providerMap.put(type, provider);
        }
        for (Class<?> i : type.getInterfaces()) {
            this.providerMap.put(i, provider);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getByKey(Object key) {
        final Provider<?> provider = this.providerMap.get(key);
        if (provider == null) {
            throw new DefinitionNotFound(key);
        } else {
            return (T) provider.provide();
        }
    }

    private <T> void addProviderFromAnnotation(Class<T> type, Factory<T> factory, Dependency annotation) {
        Provider<T> provider = null;
        switch (annotation.scope()) {
            case Singleton:
                provider = new SingletonProvider<T>(factory);
                break;
            case Prototype:
                provider = new PrototypeProvider<>(factory);
                break;
            case Thread:
                provider = new ThreadLocalProvider<T>(factory);
                break;
        }
        this.registerProvider(type, provider);
        if (!annotation.name().equals("")) {
            this.registerProvider(annotation.name(), provider);
        }
    }

    private <T> void addProviderByFactoryMethod(Class<T> returnType, Method method, Object instance, Dependency annotation) {
        // TODO: satisfy method dependencies
        this.addProviderFromAnnotation(returnType, new MethodFactory<>(method, instance), annotation);
    }

    private <T> void addProviderByConstructor(Class<T> type, Dependency annotation) {
        // TODO: dependencies graph
        Arrays.stream(type.getConstructors())
                .filter((constructor) -> Arrays.stream(constructor.getParameterTypes())
                        .allMatch((argType) -> {
                            try {
                                return this.getDependency(argType) != null;
                            } catch (DefinitionNotFound e) {
                                return false;
                            }
                        }))
                .findFirst()
                .map((constructor -> (Factory<T>) () -> {
                    try {
                        return (T) constructor.newInstance(Arrays
                                .stream(constructor.getParameterTypes())
                                .map(ApplicationContext.this::getDependency)
                                .toArray());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new LoadDefinitionException(e);
                    }
                }))
                .ifPresent((factory -> this.addProviderFromAnnotation(type, factory, annotation)));
    }
}
