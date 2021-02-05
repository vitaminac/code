package web;

import java.util.Arrays;
import java.util.List;

import converter.CompositeConverter;
import converter.IdentityConverter;
import converter.ObjectToJsonConverter;
import ioc.injection.Dependency;
import servlet.HttpControllerResolver;
import servlet.HttpRequestDispatcher;
import servlet.HttpRequestHandlerArgumentsResolver;
import servlet.HttpRequestHandlerResolver;
import servlet.HttpRequestMiddleware;
import servlet.HttpRequestMiddlewaresResolver;
import servlet.HttpResponseBodyEncodeService;
import servlet.auth.HttpBasicAuthenticationMiddleware;
import servlet.auth.PrincipleResolver;
import servlet.exception.HttpRequestExceptionMiddleware;
import servlet.exception.HttpRequestExceptionResolver;
import servlet.log.HttpRequestLogMiddleware;

public class WebInitializer {
    @Dependency
    public CompositeConverter conversionService() {
        final var compositeConverter = new CompositeConverter();
        compositeConverter.addConverter(new ObjectToJsonConverter());
        compositeConverter.addConverter(new IdentityConverter());
        return compositeConverter;
    }

    @Dependency
    public HttpBasicAuthenticationMiddleware httpBasicAuthenticationMiddleware(
            PrincipleResolver principleResolver
    ) {
        return new HttpBasicAuthenticationMiddleware(principleResolver);
    }

    @Dependency
    public HttpRequestExceptionMiddleware httpRequestExceptionMiddleware(
            final HttpRequestExceptionResolver httpRequestExceptionResolver
    ) {
        return new HttpRequestExceptionMiddleware(httpRequestExceptionResolver);
    }

    @Dependency
    public HttpRequestLogMiddleware httpRequestLogMiddleware() {
        return new HttpRequestLogMiddleware();
    }

    @Dependency
    public HttpRequestMiddlewaresResolver httpMiddlewaresResolver(
            final HttpBasicAuthenticationMiddleware httpBasicAuthenticationMiddleware,
            final HttpRequestExceptionMiddleware httpRequestExceptionMiddleware,
            final HttpRequestLogMiddleware httpRequestLogMiddleware) {
        return new HttpRequestMiddlewaresResolver() {
            @Override
            public List<HttpRequestMiddleware> resolve() {
                return Arrays.asList(
                        httpRequestLogMiddleware,
                        httpBasicAuthenticationMiddleware,
                        httpRequestExceptionMiddleware
                );
            }
        };
    }

    @Dependency
    public HttpRequestHandlerResolver httpRequestHandlerResolver(
            final HttpRequestHandlerArgumentsResolver argumentsResolver,
            final HttpResponseBodyEncodeService httpResponseBodyEncodeService,
            final HttpControllerResolver httpControllerResolver
    ) {
        return new HttpRequestHandlerResolver(argumentsResolver, httpResponseBodyEncodeService, httpControllerResolver);
    }

    @Dependency
    public HttpRequestDispatcher httpRequestDispatcher(
            final HttpRequestHandlerResolver httpRequestHandlerResolver,
            final HttpRequestMiddlewaresResolver httpRequestMiddlewaresResolver
    ) {
        return new HttpRequestDispatcher(httpRequestHandlerResolver, httpRequestMiddlewaresResolver);
    }
}
