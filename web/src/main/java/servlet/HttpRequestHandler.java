package servlet;

import java.lang.reflect.Method;

public interface HttpRequestHandler {
    Method getHandlerMethod();

    HttpResponse handle(HttpRequest request);
}
