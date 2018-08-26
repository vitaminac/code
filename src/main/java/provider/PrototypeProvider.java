package provider;

public class PrototypeProvider<T> implements Provider<T> {
    private final TypeFactory<T> prototypeFactory;

    public PrototypeProvider(TypeFactory<T> factory) {
        this.prototypeFactory = factory;
    }

    @Override
    public T provide() {
        return this.prototypeFactory.build();
    }
}
