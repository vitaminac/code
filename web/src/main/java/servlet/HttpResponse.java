package servlet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse extends HttpMessage {
    private static final int STATUS_OK = 200;
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String PLAIN_TEXT_CONTENT_TYPE = "text/plain; charset=UTF-8";
    private static final String JSON_CONTENT_TYPE = "application/json; charset=UTF-8";
    private static final String HTML_CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String BINARY_DATA_CONTENT_TYPE = "application/octet-stream";
    private static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    private static final String CONTENT_DISPOSITION_ATTACHMENT_FILENAME_FORMAT = "attachment; filename=\"%s\"";
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
                .addHeader(CONTENT_TYPE_HEADER, PLAIN_TEXT_CONTENT_TYPE)
                .setBody(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)))
                .build();
    }

    public static HttpResponse fromJSON(String json) {
        return HttpResponse.builder()
                .setStatusCode(STATUS_OK)
                .addHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
                .setBody(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)))
                .build();
    }

    public static HttpResponse fromHTML(String html) {
        return HttpResponse.builder()
                .setStatusCode(STATUS_OK)
                .addHeader(CONTENT_TYPE_HEADER, HTML_CONTENT_TYPE)
                .setBody(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)))
                .build();
    }

    public static HttpResponse fromFile(File file) throws IOException {
        return HttpResponse.builder()
                .setStatusCode(STATUS_OK)
                .addHeader(CONTENT_TYPE_HEADER, BINARY_DATA_CONTENT_TYPE)
                .addHeader(CONTENT_DISPOSITION_HEADER, String.format(CONTENT_DISPOSITION_ATTACHMENT_FILENAME_FORMAT, file.getName()))
                .setBody(new FileInputStream(file))
                .build();
    }
}
