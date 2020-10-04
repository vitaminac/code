package servlet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {
    public enum HttpRequestMethod {
        // https://tools.ietf.org/html/rfc2616#section-9
        GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE
    }

    private final HttpRequestMethod method;
    private final Map<Object, Object> context = new HashMap<>();

    public HttpRequest(HttpRequestMethod method, Map<String, String> headers, InputStream body) {
        super(headers, body);
        this.method = method;
    }

    public HttpRequestMethod getMethod() {
        return method;
    }
}
