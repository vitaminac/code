package ioc;

import config.TestPrototype;
import config.TestSingleton;
import config.TestThreadLocal;
import injection.Dependency;
import ioc.TestConstructorInject;

@Dependency
public class TestConstructorInjectImpl implements TestConstructorInject {
    public TestConstructorInjectImpl(TestPrototype prototype, TestSingleton singleton, TestThreadLocal threadLocal) {
    }
}
