import injection.ContextConfig;
import injection.Dependency;
import injection.Scope;

public class TestConfigClass implements ContextConfig {
    @Dependency
    private TestAnnotatedSingleton buildServiceSingleton() {
        return new TestAnnotatedSingleton();
    }

    @Dependency(scope = Scope.Prototype)
    private TestAnnotatedPrototype buildServicePrototype() {
        return new TestAnnotatedPrototype(99);
    }
}
