package test;

public class TestCore {
    public static void runClasses(Class<?>... classes) throws Exception {
        for (var clazz : classes) {
            if (clazz.isAnnotationPresent(TestSuite.class)) {
                final TestSuite testSuite = clazz.getAnnotation(TestSuite.class);
                final UnitTestRunner runner = testSuite.runWith().getConstructor().newInstance();
                runner.run(clazz);
            }
        }
    }
}
