package test;

public class TestCore {
    public static void runClasses(Class<?>... classes) throws Exception {
        for (var clazz : classes) {
            if (clazz.isAnnotationPresent(TestSuite.class)) {
                final TestSuite testSuite = clazz.getAnnotation(TestSuite.class);
                try (final UnitTestRunner runner = testSuite.runWith().getConstructor(Class.class).newInstance(clazz)) {
                    runner.run();
                }
            }
        }
    }
}
