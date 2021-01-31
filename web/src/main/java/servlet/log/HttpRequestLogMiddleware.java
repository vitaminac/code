package servlet.log;

import servlet.HttpRequest;
import servlet.HttpRequestHandler;
import servlet.HttpRequestMiddleware;
import servlet.HttpResponse;

public class HttpRequestLogMiddleware implements HttpRequestMiddleware {
    @Override
    public HttpResponse intercept(HttpRequest request, HttpRequestHandler handler) {
        final long startTime = System.nanoTime();
        final HttpResponse response = handler.handle(request);
        final long elapsedNanos = System.nanoTime() - startTime;
        System.out.println("Request to " + request.getPath() + " was processed by "
                + handler.getHandlerMethod().getName()
                + " in " + elapsedNanos + " nanoseconds"
        );
        return response;
    }
}
