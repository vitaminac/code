package servlet;

public interface HttpRequestMiddleware {
    HttpResponse intercept(HttpRequest request, HttpRequestHandler handler);
}
