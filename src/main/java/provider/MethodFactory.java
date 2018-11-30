package provider;

import error.LoadDefinitionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodFactory<T> implements Factory<T> {
    private final Method method;
    private final Object instance;

    public MethodFactory(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T build() {
        try {
            return (T) method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new LoadDefinitionException(e);
        }
    }
}
