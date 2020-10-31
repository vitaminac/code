package test;

import core.Utils;

import java.lang.reflect.Method;

public class ProfilerUnitTestRunner extends DefaultUnitTestRunner {
    @Override
    protected void test(Class<?> clazz, Method method, Object obj) throws Exception {
        if (method.isAnnotationPresent(UnitTestProfile.class)) {
            long startTime = System.nanoTime();
            super.test(clazz, method, obj);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            var annotation = method.getAnnotation(UnitTestProfile.class);
            if (elapsedTime > annotation.timeoutInNanoseconds()) {
                Utils.warn(this.getTestMethodId(clazz, method) + " is timeout");
            } else {
                Utils.info("Time elapsed for " + this.getTestMethodId(clazz, method) + ": " + elapsedTime / 1000 + " microsecond");
            }
        } else {
            super.test(clazz, method, obj);
        }
    }
}
