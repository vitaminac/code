package servlet;

import ioc.injection.Dependency;

import java.io.IOException;
import java.nio.charset.Charset;

@Dependency
public class HttpBodyTextConverter implements HttpResponseBodyEncoder<String>, HttpRequestBodyDecoder<String> {
    @Override
    public HttpResponse encode(String text) {
        return HttpResponse.fromPlanText(text);
    }

    @Override
    public String decode(HttpRequest request) {
        try {
            return new String(request.getBody().readAllBytes(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
