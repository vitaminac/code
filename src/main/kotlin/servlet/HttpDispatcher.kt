package servlet

class HttpDispatcher constructor(val resolver: ControllerResolver, errorHandler: ExceptionMiddleware) : Dispatcher<HttpRequest, HttpResponse> {
    val middlewares: MutableList<Middleware> = mutableListOf();

    init {
        this.register(errorHandler);
    }

    override fun serve(request: HttpRequest): HttpResponse {
        // TODO: parse http request
        var next: HttpHandler = resolver.resolve(request);
        for (middleware in this.middlewares) {
            next = object : HttpHandler {
                override fun handle(request: HttpRequest): HttpResponse {
                    return middleware.intercept(request, next);
                }
            }
        }
        return next.handle(request);
    }

    fun register(middleware: Middleware) {
        this.middlewares.add(middleware);
    }
}