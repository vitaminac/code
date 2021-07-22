package core.ioc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ApplicationContext {
    private final Map<Object, Provider<?>> map = new ConcurrentHashMap<>();

    // TODO: ApplicationEventPublisher
    // TODO: ResourcePatternResolver
    // TODO: Injector parent - Injector Hierarchy
    // TODO: scope: per request, per session, per application
    public void registerConfig(Class<?> configClass) {
        for (final Method method : configClass.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers())) {
                final Provide annotation = method.getAnnotation(Provide.class);
                if (annotation != null) {
                    this.addProviderByFactoryMethod(method.getReturnType(), method, annotation);
                }
            }
        }
    }

    private <T> void addProviderByFactoryMethod(final Class<T> returnType,
                                                final Method method,
                                                final Provide annotation) {
        final Provider<T> provider = annotation.scope().getProvider(new Supplier<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T get() {
                try {
                    return (T) method.invoke(null, Arrays
                            .stream(method.getParameterTypes())
                            .map(ApplicationContext.this::resolveDependency)
                            .toArray());
                } catch (final Exception e) {
                    throw new LoadDefinitionException(e);
                }
            }
        });
        this.registerDependency(returnType, provider);
        if (annotation.name().length() > 0) this.registerDependency(annotation.name(), provider);
    }

    public <T> T resolveDependency(final Class<T> type) {
        return this.findByKey(type);
    }

    public <T> T resolveDependency(final String name) {
        return this.findByKey(name);
    }

    private <T> void registerDependency(final String name,
                                        final Provider<T> provider) {
        if (!this.map.containsKey(name)) {
            this.map.put(name, provider);
        } else {
            throw new DuplicateDefinitionException(name);
        }
    }

    private <T> void registerDependency(final Class<? super T> type,
                                        final Provider<T> provider) {
        this.map.put(type, provider);
        for (Class<?> i : type.getInterfaces()) {
            this.map.put(i, provider);
        }
    }

    private <T> T findByKey(final Object key) {
        @SuppressWarnings("unchecked") final Provider<T> provider = (Provider<T>) this.map.get(key);
        if (provider == null) {
            throw new DefinitionNotFound(key);
        } else {
            return provider.provide();
        }
    }
}
