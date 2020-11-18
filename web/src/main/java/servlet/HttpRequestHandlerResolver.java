package servlet;

import core.Reflections;
import ioc.injection.Dependency;

import java.lang.reflect.InvocationTargetException;

@Dependency
public class HttpRequestHandlerResolver {
    private final HttpRequestHandlerArgumentsResolver argumentsResolver;
    private final HttpResponseBodyEncodeService httpResponseBodyEncodeService;
    private final Object[] controllers;

    public HttpRequestHandlerResolver(final HttpRequestHandlerArgumentsResolver argumentsResolver,
                                      final HttpResponseBodyEncodeService httpResponseBodyEncodeService,
                                      final Object... controllers) {
        this.argumentsResolver = argumentsResolver;
        this.httpResponseBodyEncodeService = httpResponseBodyEncodeService;
        this.controllers = controllers;
    }

    public HttpRequestHandler resolve(final HttpRequest request) {
        for (var controller : controllers) {
            for (var method : Reflections.getMethodsAnnotatedWith(controller.getClass(), RequestMapping.class)) {
                final var requestMapping = method.getAnnotation(RequestMapping.class);
                if (request.getPath().matches(requestMapping.path())) {
                    return new HttpRequestHandler() {
                        @Override
                        public HttpResponse handle(final HttpRequest request) {
                            final var arguments = argumentsResolver.resolveArguments(request, method);
                            try {
                                final var returnVal = method.invoke(controller, arguments);
                                if (method.isAnnotationPresent(HttpResponseBody.class)) {
                                    final HttpResponseBody annotation = method.getAnnotation(HttpResponseBody.class);
                                    return HttpRequestHandlerResolver.this.httpResponseBodyEncodeService.encode(returnVal, annotation);
                                } else {
                                    return (HttpResponse) returnVal;
                                }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException("can not invoke " + controller.getClass().getCanonicalName() + "#" + method.getName());
                            }
                        }
                    };
                }
            }
        }
        throw new RuntimeException("can not resolve request for " + request.getMethod() + " " + request.getPath());
    }
}
