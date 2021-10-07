package test;

import core.util.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUnitTestRunner extends UnitTestRunner {
    private final Object testsuiteInstance;
    private final List<Method> teardownTestSuiteMethods;
    private final List<Method> setupMethods;
    private final List<Method> teardownMethods;
    private final List<Method> unittestMethods;

    public DefaultUnitTestRunner(Class<?> clazz) throws Exception {
        super(clazz);
        for (var setupClassMethod :
                Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(BeforeTestSuite.class))
                        .filter(this::filterAndWarnInvalidUsage)
                        .filter(method -> Modifier.isStatic(method.getModifiers()))
                        .collect(Collectors.toList())) {
            setupClassMethod.invoke(null);
        }
        final var constructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Default constructor must exist"));
        this.testsuiteInstance = constructor.newInstance();
        this.teardownTestSuiteMethods = Arrays.stream(this.testsuiteInstance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AfterTestSuite.class))
                .filter(this::filterAndWarnInvalidUsage)
                .filter(method -> Modifier.isStatic(method.getModifiers()))
                .collect(Collectors.toList());
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

    protected void test(Method method) throws Exception {
        method.invoke(this.testsuiteInstance);
    }

    protected void setup(List<Method> setupMethods) throws Exception {
        for (var setupMethod : setupMethods) {
            setupMethod.invoke(this.testsuiteInstance);
        }
    }

    protected void teardown(List<Method> teardownMethods) throws Exception {
        for (var teardownMethod : teardownMethods) {
            teardownMethod.invoke(this.testsuiteInstance);
        }
    }

    @Override
    public void run() throws Exception {
        for (var method : this.unittestMethods) {
            final var annotation = method.getAnnotation(UnitTest.class);
            if (annotation.disabled()) {
                Utils.warn(this.getTestMethodId(method) + " is disabled");
            } else {
                this.setup(this.setupMethods);
                this.test(method);
                this.teardown(this.teardownMethods);
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

    @Override
    public void close() throws Exception {
        for (var teardownTestSuite : this.teardownTestSuiteMethods) {
            teardownTestSuite.invoke(null);
        }
    }
}
