package servlet.exception;

import servlet.HttpRequest;
import servlet.HttpRequestHandler;
import servlet.HttpRequestMiddleware;
import servlet.HttpResponse;

public class HttpRequestExceptionMiddleware implements HttpRequestMiddleware {
    private final HttpRequestExceptionResolver httpRequestExceptionResolver;

    public HttpRequestExceptionMiddleware(HttpRequestExceptionResolver httpRequestExceptionResolver) {
        this.httpRequestExceptionResolver = httpRequestExceptionResolver;
    }

    @Override
    public HttpResponse intercept(HttpRequest request, HttpRequestHandler handler) {
        try {
            return handler.handle(request);
        } catch (final Exception e) {
            return httpRequestExceptionResolver.resolve(e);
        }
    }
}
