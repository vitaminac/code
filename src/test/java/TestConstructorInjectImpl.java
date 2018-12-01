import config.TestPrototype;
import config.TestSingleton;
import injection.Dependency;

@Dependency
public class TestConstructorInjectImpl implements TestConstructorInject {
    public TestConstructorInjectImpl(TestPrototype prototype, TestSingleton singleton) {
    }
}
