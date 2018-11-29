import injection.ContextConfig;
import injection.Injectable;
import injection.Scope;

public class TestConfigClass implements ContextConfig {
    @Injectable
    private TestAnnotatedSingleton buildServiceSingleton() {
        return new TestAnnotatedSingleton();
    }

    @Injectable(scope = Scope.Prototype)
    private TestAnnotatedPrototype buildServicePrototype() {
        return new TestAnnotatedPrototype(99);
    }
}
