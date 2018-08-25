package provider;

import provider.Provider;

public interface FactoryProvider<T> extends Provider<T> {
    T build();

    @Override
    default T provide() {
        return this.build();
    }
}
