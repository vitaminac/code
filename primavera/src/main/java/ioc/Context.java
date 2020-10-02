package ioc;

import ioc.injection.ContextConfig;

public interface Context {
    <Config extends ContextConfig> void addConfig(Config config);

    Locator getLocator();

    void addDependencies(Class... types);
}
