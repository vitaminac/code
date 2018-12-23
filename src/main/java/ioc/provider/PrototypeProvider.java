package ioc.provider;

public class PrototypeProvider<T> implements Provider<T> {
    private final Factory<T> prototypeFactory;

    public PrototypeProvider(Factory<T> factory) {
        this.prototypeFactory = factory;
    }

    @Override
    public T provide() {
        return this.prototypeFactory.build();
    }
}
