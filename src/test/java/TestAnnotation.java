import injection.Injectable;
import injection.Scope;

public class TestAnnotation {
    @Injectable
    private TestAnnotatedSingleton buildServiceSingleton() {
        return new TestAnnotatedSingleton();
    }

    @Injectable(scope = Scope.Prototype)
    private TestAnnotatedPrototype buildServicePrototype() {
        return new TestAnnotatedPrototype(99);
    }
}
