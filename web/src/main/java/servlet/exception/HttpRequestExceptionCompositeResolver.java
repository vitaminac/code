package servlet.exception;

import java.util.ArrayList;
import java.util.List;

import servlet.HttpResponse;

public class HttpRequestExceptionCompositeResolver implements HttpRequestExceptionResolver {
    public List<HttpRequestExceptionResolver> httpRequestExceptionResolvers = new ArrayList<>();

    @Override
    public HttpResponse resolve(final Exception e) {
        for (final var httpRequestExceptionResolver : this.httpRequestExceptionResolvers) {
            final var response = httpRequestExceptionResolver.resolve(e);
            if (response != null) {
                return response;
            }
        }
        return null;
    }

    public void addHttpRequestExceptionResolver(final HttpRequestExceptionResolver httpRequestExceptionResolver) {
        this.httpRequestExceptionResolvers.add(httpRequestExceptionResolver);
    }
}
