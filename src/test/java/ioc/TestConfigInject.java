package ioc;

import config.TestPrototype;
import config.TestSingleton;
import config.TestThreadLocal;
import injection.ContextConfig;
import injection.Dependency;

public class TestConfigInject implements ContextConfig {
    @Dependency
    public TestFactoryMethodInject buildFactoryMethodInject(TestPrototype prototype, TestSingleton singleton, TestThreadLocal threadLocal) {
        return new TestFactoryMethodInjectImpl(prototype, singleton, threadLocal);
    }
}
