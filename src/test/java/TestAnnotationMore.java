import injection.Injectable;
import injection.Scope;

public class TestAnnotationMore {
    @Injectable
    private TestAnnotatedSingletonMore buildServiceSingleton() {
        return new TestAnnotatedSingletonMore();
    }

    @Injectable(scope = Scope.Prototype)
    private TestAnnotatedPrototypeMore buildServicePrototype() {
        return new TestAnnotatedPrototypeMore(99);
    }
}
