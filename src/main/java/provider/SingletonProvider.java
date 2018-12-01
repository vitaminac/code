package provider;


public class SingletonProvider<T> implements Provider<T> {
    private T instance;
    private final Factory<T> factory;

    public SingletonProvider(Factory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T provide() {
        if (this.instance == null) {
            this.instance = factory.build();
        }
        return this.instance;
    }
}
