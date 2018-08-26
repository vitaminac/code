package provider;

import provider.Provider;

public interface TypeFactory<T> {
    T build();
}
