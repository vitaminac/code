package test;

import core.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class TestCore {
    private static void test(Method method, Object obj) throws Exception {
        if (!Modifier.isPublic(method.getModifiers())) {
            Utils.warn("Test method has to be public");
        } else if (method.getParameterCount() > 0) {
            Utils.warn("Test method should not have any parameters");
        } else {
            method.invoke(obj);
        }
    }

    private static <T> void test(Class<T> clazz) throws Exception {
        if (clazz.isAnnotationPresent(TestSuite.class)) {
            var constructor = Arrays.stream(clazz.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 0)
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Default constructor must exist"));
            var obj = constructor.newInstance();
            for (var method : clazz.getMethods()) {
                if (method.isAnnotationPresent(UnitTest.class)) {
                    UnitTest annotation = method.getAnnotation(UnitTest.class);
                    if (!annotation.disabled()) {
                        test(method, obj);
                    } else {
                        Utils.warn(clazz.getCanonicalName() + "#" + method.getName() + " is disabled");
                    }
                }
            }
        }
    }

    public static void runClasses(Class<?>... classes) throws Exception {
        for (var clazz : classes) {
            test(clazz);
        }
    }
}
