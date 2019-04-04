package ioc;

import ioc.config.TestPrototype;
import ioc.config.TestSingleton;
import ioc.config.TestThreadLocal;

public class TestFactoryMethodInjectImpl implements TestFactoryMethodInject {
    public TestFactoryMethodInjectImpl(TestPrototype prototype, TestSingleton singleton, TestThreadLocal threadLocal) {
    }
}
