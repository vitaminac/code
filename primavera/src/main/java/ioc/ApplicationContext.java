package ioc;

import ioc.error.DefinitionNotFound;
import ioc.error.LoadDefinitionException;
import ioc.injection.ContextConfig;
import ioc.injection.Dependency;
import org.reflections.Reflections;
import ioc.provider.Factory;
import ioc.provider.PrototypeProvider;
import ioc.provider.Provider;
import ioc.provider.SingletonProvider;
import ioc.provider.ThreadLocalProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public enum ApplicationContext implements Context {
    CONTEXT(new LocatorImpl());

    // TODO: ApplicationEventPublisher
    // TODO: ResourcePatternResolver
    // TODO: Injector parent - Injector Hierarchy
    // TODO: scope: per request, per session, per application
    // TODO: run with init method of ioc.config file

    private final Locator locator;

    ApplicationContext(Locator locator) {
        this.locator = locator;
        this.locator.register(ApplicationContext.class, new SingletonProvider<>(() -> this));

        Reflections reflections = new Reflections("");
        Set<Class<? extends ContextConfig>> configs = reflections.getSubTypesOf(ContextConfig.class);
        Set<Class<?>> dependencies = reflections.getTypesAnnotatedWith(Dependency.class);
        this.addDependencies(dependencies.toArray(new Class[0]));
        for (Class<? extends ContextConfig> config : configs) {
            try {
                this.addConfig(config.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public <Config extends ContextConfig> void addConfig(Config config) {
        for (Method method : config.getClass().getDeclaredMethods()) {
            Dependency annotation = method.getAnnotation(Dependency.class);
            if (annotation != null) {
                method.setAccessible(true);
                this.addProviderByFactoryMethod(method.getReturnType(), method, config, annotation);
            }
        }
    }

    public void addDependencies(Class... types) {
        for (Class<?> type : types) {
            Dependency annotation = type.getAnnotation(Dependency.class);
            if (annotation != null) {
                this.addProviderByConstructor(type, annotation);
            }
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
        this.locator.register(type, provider);
        if (!annotation.name().equals("")) {
            this.locator.register(annotation.name(), provider);
        }
    }

    private <T> void addProviderByFactoryMethod(Class<T> returnType, Method method, Object instance, Dependency annotation) {
        this.addProviderFromAnnotation(returnType, new Factory<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T build() {
                try {
                    return (T) method.invoke(instance, Arrays
                            .stream(method.getParameterTypes())
                            .map(ApplicationContext.this.locator::find)
                            .toArray());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new LoadDefinitionException(e);
                }
            }
        }, annotation);
    }

    private <T> void addProviderByConstructor(Class<T> type, Dependency annotation) {
        // TODO: dependencies graph
        this.addProviderFromAnnotation(type, new Factory<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T build() {
                return Arrays.stream(type.getConstructors())
                        .filter((constructor) -> Arrays.stream(constructor.getParameterTypes())
                                .allMatch((argType) -> {
                                    try {
                                        return ApplicationContext.this.locator.find(argType) != null;
                                    } catch (DefinitionNotFound e) {
                                        return false;
                                    }
                                }))
                        .findFirst()
                        .map((constructor -> {
                            try {
                                return (T) constructor.newInstance(Arrays
                                        .stream(constructor.getParameterTypes())
                                        .map(ApplicationContext.this.locator::find)
                                        .toArray());
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                throw new LoadDefinitionException(e);
                            }
                        }))
                        .orElseThrow(() -> new LoadDefinitionException("Can not satisfy all the required dependencies"));
            }
        }, annotation);
    }

    public Locator getLocator() {
        return locator;
    }
}
