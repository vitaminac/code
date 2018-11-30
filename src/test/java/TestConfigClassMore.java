import injection.ContextConfig;
import injection.Dependency;
import injection.Scope;

public class TestConfigClassMore implements ContextConfig {
    @Dependency
    private TestAnnotatedSingletonMore buildServiceSingleton() {
        return new TestAnnotatedSingletonMore();
    }

    @Dependency(scope = Scope.Prototype)
    private TestAnnotatedPrototypeMore buildServicePrototype() {
        return new TestAnnotatedPrototypeMore(99);
    }
}
