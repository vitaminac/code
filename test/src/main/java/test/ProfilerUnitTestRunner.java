package test;

import core.Utils;

import java.lang.reflect.Method;

public class ProfilerUnitTestRunner extends DefaultUnitTestRunner {
    public ProfilerUnitTestRunner(Class<?> clazz) throws Exception {
        super(clazz);
    }

    @Override
    protected void test(Method method) throws Exception {
        if (method.isAnnotationPresent(UnitTestProfile.class)) {
            long startTime = System.nanoTime();
            super.test(method);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            var annotation = method.getAnnotation(UnitTestProfile.class);
            if (elapsedTime > annotation.timeoutInNanoseconds()) {
                Utils.warn(this.getTestMethodId(method) + " is timeout");
            } else {
                Utils.info("Time elapsed for " + this.getTestMethodId(method) + ": " + elapsedTime / 1000 + " microsecond");
            }
        } else {
            super.test(method);
        }
    }
}
