import injection.ContextConfig;
import injection.Injectable;
import injection.Scope;

public class TestConfigClassMore implements ContextConfig {
    @Injectable
    private TestAnnotatedSingletonMore buildServiceSingleton() {
        return new TestAnnotatedSingletonMore();
    }

    @Injectable(scope = Scope.Prototype)
    private TestAnnotatedPrototypeMore buildServicePrototype() {
        return new TestAnnotatedPrototypeMore(99);
    }
}
