package provider;

public class DefaultTypeFactory<T> implements TypeFactory<T> {
    private final Class<T> type;

    public DefaultTypeFactory(Class<T> type) {
        this.type = type;
    }

    @Override
    public T build() {
        try {
            return this.type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
