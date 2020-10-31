package test;

import core.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class DefaultUnitTestRunner implements UnitTestRunner {
    protected String getTestMethodId(Class<?> clazz, Method method) {
        return clazz.getCanonicalName() + "#" + method.getName();
    }

    protected void test(Class<?> clazz, Method method, Object obj) throws Exception {
        method.invoke(obj);
    }

    @Override
    public void run(Class<?> clazz) throws Exception {
        final var constructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Default constructor must exist"));
        final var obj = constructor.newInstance();
        for (var method : clazz.getMethods()) {
            if (method.isAnnotationPresent(UnitTest.class)) {
                final var annotation = method.getAnnotation(UnitTest.class);
                if (annotation.disabled()) {
                    Utils.warn(this.getTestMethodId(clazz, method) + " is disabled");
                } else if (!Modifier.isPublic(method.getModifiers())) {
                    Utils.warn("Test method has to be public");
                } else if (method.getParameterCount() > 0) {
                    Utils.warn("Test method should not have any parameters");
                } else {
                    this.test(clazz, method, obj);
                }
            }
        }
    }
}
