package servlet;

import ioc.injection.Dependency;

@Dependency
public class ExceptionHttpRequestMiddleware implements HttpRequestMiddleware {
    @Override
    public HttpResponse intercept(HttpRequest request, HttpRequestHandler handler) {
        try {
            return handler.handle(request);
        } catch (Exception e) {
            throw new UnsupportedOperationException("TODO: to be implemented - render the default error page with stacktrace");
        }
    }
}
