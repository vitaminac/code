package web;

import converter.ConversionService;
import converter.IdentityConverter;
import converter.ObjectToJsonConverter;
import ioc.injection.Dependency;

public class WebInitializer {
    @Dependency
    public ConversionService conversionService() {
        return new ConversionService(
                new ObjectToJsonConverter(),
                new IdentityConverter()
        );
    }
}
