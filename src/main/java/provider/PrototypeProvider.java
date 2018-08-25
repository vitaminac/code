package provider;

public class PrototypeProvider<T extends Prototype> implements Provider<T> {
    private final T prototype;

    public PrototypeProvider(T prototype) {
        this.prototype = prototype;
    }

    @Override
    public T provide() {
        return (T) this.prototype.clone();
    }
}
