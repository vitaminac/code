package servlet;

import ioc.injection.Dependency;

@Dependency
public class HttpBodyJsonConverter implements HttpResponseBodyEncoder<Object>, HttpRequestBodyDecoder<Object> {
    @Override
    public Object decode(HttpRequest request) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public HttpResponse convert(Object o) {
        throw new UnsupportedOperationException("TODO");
    }
}
