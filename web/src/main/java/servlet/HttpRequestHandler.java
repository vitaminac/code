package servlet;

public interface HttpRequestHandler {
    HttpResponse handle(HttpRequest request);
}
