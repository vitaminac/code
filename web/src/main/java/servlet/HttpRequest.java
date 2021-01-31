package servlet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {
    public enum HttpRequestMethod {
        // https://tools.ietf.org/html/rfc2616#section-9
        GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE
    }

    private final String path;
    private final HttpRequestMethod method;
    private final Map<String, Object> context = new HashMap<>();

    public HttpRequest(HttpRequestMethod method, String path, Map<String, String> headers, InputStream body) {
        super(headers, body);
        this.path = path;
        this.method = method;
    }

    public HttpRequestMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Object getContextParam(String parameterName) {
        return this.context.get(parameterName);
    }

    public void putContextParam(String parameterName, Object parameterValue) {
        this.context.put(parameterName, parameterValue);
    }
}
