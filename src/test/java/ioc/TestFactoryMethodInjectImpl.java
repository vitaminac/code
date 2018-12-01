package ioc;

import config.TestPrototype;
import config.TestSingleton;
import config.TestThreadLocal;

public class TestFactoryMethodInjectImpl implements TestFactoryMethodInject {
    public TestFactoryMethodInjectImpl(TestPrototype prototype, TestSingleton singleton, TestThreadLocal threadLocal) {
    }
}
