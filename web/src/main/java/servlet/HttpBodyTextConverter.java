package servlet;

import ioc.injection.Dependency;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Dependency
public class HttpBodyTextConverter implements HttpResponseBodyEncoder<String>, HttpRequestBodyDecoder<String> {
    @Override
    public HttpResponse convert(String text) {
        return HttpResponse.fromPlanText(text);
    }

    @Override
    public String decode(HttpRequest request) {
        try {
            return new String(request.getBody().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
