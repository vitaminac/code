package ioc.config;

import ioc.injection.ContextConfig;
import ioc.injection.Dependency;
import ioc.injection.Scope;

public class TestConfigClass implements ContextConfig {
    @Dependency
    private TestSingleton buildServiceSingleton() {
        return new TestSingletonImpl();
    }

    @Dependency(scope = Scope.Prototype)
    private TestPrototype buildServicePrototype() {
        return new TestPrototypeImpl(99);
    }

    @Dependency(scope = Scope.Thread)
    private TestThreadLocal buildServiceThread() {
        return new TestThreadLocalImpl();
    }

    @Dependency(name = "myConfiguredName")
    private TestNamedDependency buildNamedService() {
        return new TestNamedDependency();
    }
}
