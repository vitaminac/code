package ioc;

import ioc.config.TestPrototype;
import ioc.config.TestSingleton;
import ioc.config.TestThreadLocal;
import ioc.injection.ContextConfig;
import ioc.injection.Dependency;

public class TestConfigInject implements ContextConfig {
    @Dependency
    public TestFactoryMethodInject buildFactoryMethodInject(TestPrototype prototype, TestSingleton singleton, TestThreadLocal threadLocal) {
        return new TestFactoryMethodInjectImpl(prototype, singleton, threadLocal);
    }
}
