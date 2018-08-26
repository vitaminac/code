package provider;


public class SingletonProvider<T> implements Provider<T> {
    private final T instance;

    public SingletonProvider(TypeFactory<T> factory) {
        this.instance = factory.build();
    }

    @Override
    public T provide() {
        return this.instance;
    }
}
