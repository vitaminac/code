package provider;


public class ThreadLocalProvider<T> implements Provider<T> {
    private final ThreadLocal<T> local;

    public ThreadLocalProvider(TypeFactory<T> factory) {
        this.local = ThreadLocal.withInitial(factory::build);
    }

    @Override
    public T provide() {
        return this.local.get();
    }
}
