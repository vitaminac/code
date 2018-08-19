package servlet

class HttpDispatcher constructor(val resolver: ControllerResolver, errorHandler: ExceptionMiddleware) : Dispatcher<HttpRequest, HttpResponse> {
    var handler: HttpHandler = object : HttpHandler {
        override fun handle(request: HttpRequest): HttpResponse {
            val handler = resolver.resolve(request);
            return handler.handle(request);
        }
    }

    init {
        this.register(errorHandler);
    }

    override fun serve(request: HttpRequest): HttpResponse {
        // TODO: parse http request
        return this.handler.handle(request);
    }

    fun register(middleware: Middleware) {
        // TODO: does it catch this or next?
        val next = handler;
        this.handler = object : HttpHandler {
            override fun handle(request: HttpRequest): HttpResponse {
                return middleware.intercept(request, next);
            }
        }
    }
}