package ioc;

import ioc.provider.Provider;

public interface Locator {
    <T> T find(Class<T> type);

    <T> T find(String name);

    <T> void register(String name, Provider<T> provider);

    <T> void register(Class<? super T> type, Provider<T> provider);
}
