import injection.ContextConfig;
import provider.Provider;

public interface Context {
    <T extends ContextConfig> void registerConfig(Class<T> config);

    void registerDependencies(Class... types);

    <T> T getDependency(Class<T> type);

    <T> T getDependencyByName(String name);

    <T> void registerProvider(String name, Provider<T> provider);

    <T> void registerProvider(Class<? super T> type, Provider<T> provider);
}
