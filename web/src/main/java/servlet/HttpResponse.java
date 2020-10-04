package servlet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse extends HttpMessage {
    private static final int STATUS_OK = 200;
    private final int statusCode;

    private HttpResponse(Map<String, String> headers, int statusCode, InputStream body) {
        super(headers, body);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public static class HttpResponseBuilder {
        private int statusCode = -1;
        private final Map<String, String> headers = new HashMap<>();
        private InputStream body;

        private HttpResponseBuilder() {
        }

        public HttpResponseBuilder addHeader(String header, String value) {
            this.headers.put(header, value);
            return this;
        }

        public HttpResponseBuilder setBody(InputStream body) {
            this.body = body;
            return this;
        }

        public HttpResponseBuilder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public HttpResponse build() {
            if (this.statusCode < 100 || this.statusCode >= 600)
                throw new RuntimeException(String.format("http status code %d is invalid ", statusCode));
            if (this.body == null)
                throw new RuntimeException("http body must be non null");
            final var body = this.body;
            this.body = null; // prevent being reused
            return new HttpResponse(this.headers, this.statusCode, body);
        }
    }

    public static HttpResponseBuilder builder() {
        return new HttpResponseBuilder();
    }

    public static HttpResponse fromPlanText(String text) {
        return HttpResponse.builder()
                .setStatusCode(STATUS_OK)
                .setBody(new ByteArrayInputStream(text.getBytes(Charset.defaultCharset())))
                .build();
    }
}
