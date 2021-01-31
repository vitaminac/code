package servlet;

import java.lang.reflect.Method;
import java.util.List;

public class HttpRequestDispatcher {
    private final HttpRequestHandlerResolver resolver;
    private final List<HttpRequestMiddleware> httpRequestMiddlewares;

    public HttpRequestDispatcher(HttpRequestHandlerResolver resolver,
                                 HttpRequestMiddlewaresResolver httpRequestMiddlewaresResolver) {
        this.resolver = resolver;
        this.httpRequestMiddlewares = httpRequestMiddlewaresResolver.resolve();
    }

    public HttpResponse dispatch(final HttpRequest request) {
        final var initialHandler = resolver.resolve(request);
        var handler = initialHandler;
        for (final var middleware : httpRequestMiddlewares) {
            final var nextHandler = handler;
            handler = new HttpRequestHandler() {
                @Override
                public Method getHandlerMethod() {
                    return initialHandler.getHandlerMethod();
                }

                @Override
                public HttpResponse handle(final HttpRequest request) {
                    return middleware.intercept(request, nextHandler);
                }
            };
        }
        return handler.handle(request);
    }
}
