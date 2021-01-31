package web;

import java.util.Arrays;
import java.util.List;

import converter.ConversionService;
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

public class WebInitializer {
    @Dependency
    public ConversionService conversionService() {
        return new ConversionService(
                new ObjectToJsonConverter(),
                new IdentityConverter()
        );
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
    public HttpRequestMiddlewaresResolver httpMiddlewaresResolver(
            final HttpBasicAuthenticationMiddleware httpBasicAuthenticationMiddleware,
            final HttpRequestExceptionMiddleware httpRequestExceptionMiddleware) {
        return new HttpRequestMiddlewaresResolver() {
            @Override
            public List<HttpRequestMiddleware> resolve() {
                return Arrays.asList(
                        httpRequestExceptionMiddleware,
                        httpBasicAuthenticationMiddleware
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
