package servlet.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import servlet.HttpRequest;
import servlet.HttpRequestHandler;
import servlet.HttpRequestMiddleware;
import servlet.HttpResponse;

import static servlet.HttpHeader.AUTHORIZATION;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication#basic_authentication_scheme
 */
public class HttpBasicAuthenticationMiddleware implements HttpRequestMiddleware {
    private final PrincipleResolver principleResolver;

    public HttpBasicAuthenticationMiddleware(PrincipleResolver principleResolver) {
        this.principleResolver = principleResolver;
    }

    @Override
    public HttpResponse intercept(HttpRequest request, HttpRequestHandler handler) {
        if (handler.getHandlerMethod().isAnnotationPresent(RequireAuthentication.class)) {
            if (request.getHeader(AUTHORIZATION) != null
                    && request.getHeader(AUTHORIZATION).startsWith("BASIC ")) {
                final String userPass = new String(
                        Base64.getDecoder().decode(request.getHeader(AUTHORIZATION).substring(6)),
                        StandardCharsets.UTF_8);
                final String[] split = userPass.split(":");
                request.putContextParam("principle", principleResolver.resolve(split[0], split[1]));
            }
        }
        return handler.handle(request);
    }
}
