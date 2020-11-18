package servlet;

import java.lang.reflect.Method;

public interface HttpRequestHandlerArgumentsResolver {
    Object[] resolveArguments(HttpRequest request, Method method);
}
