package ioc.provider;

import error.LoadDefinitionException;

public class DefaultFactory<T> implements Factory<T> {
    private final Class<T> type;

    public DefaultFactory(Class<T> type) {
        this.type = type;
    }

    @Override
    public T build() {
        try {
            return this.type.newInstance();
        } catch (Exception e) {
            throw new LoadDefinitionException(e);
        }
    }
}
