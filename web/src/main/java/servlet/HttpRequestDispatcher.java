package servlet;

import ioc.injection.Inject;

import java.util.List;

public class HttpRequestDispatcher {
    private final HttpRequestHandlerResolver resolver;
    private final List<HttpRequestMiddleware> httpRequestMiddlewares;

    @Inject
    public HttpRequestDispatcher(HttpRequestHandlerResolver resolver, List<HttpRequestMiddleware> httpRequestMiddlewares) {
        this.resolver = resolver;
        this.httpRequestMiddlewares = httpRequestMiddlewares;
    }

    private static class MiddlewareHttpRequestHandler implements HttpRequestHandler {
        private final HttpRequestMiddleware httpRequestMiddleware;
        private final HttpRequestHandler handler;

        private MiddlewareHttpRequestHandler(HttpRequestMiddleware httpRequestMiddleware, HttpRequestHandler handler) {
            this.httpRequestMiddleware = httpRequestMiddleware;
            this.handler = handler;
        }

        @Override
        public HttpResponse handle(HttpRequest request) {
            return this.httpRequestMiddleware.intercept(request, this.handler);
        }
    }

    public HttpResponse dispatch(HttpRequest request) {
        var handler = resolver.resolve(request);
        for (var middleware : httpRequestMiddlewares) {
            handler = new MiddlewareHttpRequestHandler(middleware, handler);
        }
        return handler.handle(request);
    }
}
