package test;

import core.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUnitTestRunner extends UnitTestRunner {
    private final Object testsuiteInstance;
    private final List<Method> setupMethods;
    private final List<Method> teardownMethods;
    private final List<Method> unittestMethods;

    public DefaultUnitTestRunner(Class<?> clazz) throws Exception {
        super(clazz);
        final var constructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Default constructor must exist"));
        this.testsuiteInstance = constructor.newInstance();
        this.setupMethods = Arrays.stream(this.testsuiteInstance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(BeforeEachUnitTest.class))
                .filter(this::filterAndWarnInvalidUsage)
                .collect(Collectors.toList());
        this.teardownMethods = Arrays.stream(this.testsuiteInstance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AfterEachUnitTest.class))
                .filter(this::filterAndWarnInvalidUsage)
                .collect(Collectors.toList());
        this.unittestMethods = Arrays.stream(this.testsuiteInstance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(UnitTest.class))
                .filter(this::filterAndWarnInvalidUsage)
                .collect(Collectors.toList());
    }

    protected String getTestMethodId(Method method) {
        return this.clazz.getCanonicalName() + "#" + method.getName();
    }

    protected void test(Method method, Object obj) throws Exception {
        method.invoke(obj);
    }

    protected void setup(Object obj, List<Method> setupMethods) throws Exception {
        for (var setupMethod : setupMethods) {
            setupMethod.invoke(obj);
        }
    }

    protected void teardown(Object obj, List<Method> teardownMethods) throws Exception {
        for (var teardownMethod : teardownMethods) {
            teardownMethod.invoke(obj);
        }
    }

    @Override
    public void run() throws Exception {
        for (var method : this.unittestMethods) {
            final var annotation = method.getAnnotation(UnitTest.class);
            if (annotation.disabled()) {
                Utils.warn(this.getTestMethodId(method) + " is disabled");
            } else {
                this.setup(this.testsuiteInstance, this.setupMethods);
                this.test(method, this.testsuiteInstance);
                this.teardown(this.testsuiteInstance, this.teardownMethods);
            }
        }
    }

    private boolean filterAndWarnInvalidUsage(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            Utils.warn(this.getTestMethodId(method) + " has to be public");
            return false;
        } else if (method.getParameterCount() > 0) {
            Utils.warn(this.getTestMethodId(method) + " should not have any parameters");
            return false;
        } else {
            return true;
        }
    }
}
