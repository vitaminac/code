package servlet;

import java.lang.reflect.Method;
import java.util.List;

import core.Reflections;

public class HttpRequestHandlerResolver {
    private final HttpRequestHandlerArgumentsResolver argumentsResolver;
    private final HttpResponseBodyEncodeService httpResponseBodyEncodeService;
    private final List<Object> controllers;

    public HttpRequestHandlerResolver(final HttpRequestHandlerArgumentsResolver argumentsResolver,
                                      final HttpResponseBodyEncodeService httpResponseBodyEncodeService,
                                      final HttpControllerResolver httpControllerResolver) {
        this.argumentsResolver = argumentsResolver;
        this.httpResponseBodyEncodeService = httpResponseBodyEncodeService;
        this.controllers = httpControllerResolver.resolve();
    }

    public HttpRequestHandler resolve(final HttpRequest request) {
        for (var controller : controllers) {
            for (final var method : Reflections.getMethodsAnnotatedWith(controller.getClass(), RequestMapping.class)) {
                final var requestMapping = method.getAnnotation(RequestMapping.class);
                if (request.getPath().matches(requestMapping.path())) {
                    return new HttpRequestHandler() {
                        @Override
                        public Method getHandlerMethod() {
                            return method;
                        }

                        @Override
                        public HttpResponse handle(final HttpRequest request) {
                            final var arguments = argumentsResolver.resolveArguments(request, method);
                            final var returnVal = Reflections.invoke(controller, method, arguments);
                            if (method.isAnnotationPresent(HttpResponseBody.class)) {
                                final HttpResponseBody annotation = method.getAnnotation(HttpResponseBody.class);
                                return HttpRequestHandlerResolver.this.httpResponseBodyEncodeService.encode(returnVal, annotation);
                            } else {
                                return (HttpResponse) returnVal;
                            }
                        }
                    };
                }
            }
        }
        throw new RuntimeException("can not resolve request for " + request.getMethod() + " " + request.getPath());
    }
}
