package servlet;

import java.io.InputStream;
import java.util.Map;

public class HttpMessage {
    private final Map<String, String> headers;
    private final InputStream body;

    public HttpMessage(Map<String, String> headers, InputStream body) {
        this.headers = headers;
        this.body = body;
    }

    public String getHeader(String header) {
        return this.headers.get(header);
    }

    public InputStream getBody() {
        return this.body;
    }
}
