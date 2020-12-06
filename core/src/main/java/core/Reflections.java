package core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class Reflections {
    public static java.util.List<Method> getMethodsAnnotatedWith(
            Class<?> clazz,
            final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<>();
        while (clazz != Object.class) {
            for (final Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    @SuppressWarnings("unchecked")
    public static <T> T defaultValueOf(Class<T> clazz) {
        if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            return (T) Boolean.FALSE;
        } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
            return (T) Byte.valueOf((byte) 0);
        } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
            return (T) Short.valueOf((short) 0);
        } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(0);
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return (T) Long.valueOf(0L);
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return (T) Float.valueOf(0.0f);
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return (T) Double.valueOf(0.0);
        } else {
            return null;
        }
    }
}
