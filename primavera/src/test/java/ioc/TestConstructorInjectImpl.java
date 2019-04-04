package ioc;

import ioc.config.TestPrototype;
import ioc.config.TestSingleton;
import ioc.config.TestThreadLocal;
import ioc.injection.Dependency;

@Dependency
public class TestConstructorInjectImpl implements TestConstructorInject {
    public TestConstructorInjectImpl(TestPrototype prototype, TestSingleton singleton, TestThreadLocal threadLocal) {
    }
}
