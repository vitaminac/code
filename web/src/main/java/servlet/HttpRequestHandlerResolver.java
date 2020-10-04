package servlet;

public interface HttpRequestHandlerResolver {
    HttpRequestHandler resolve(HttpRequest request);
}
