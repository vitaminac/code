package servlet

class HttpDispatcher constructor(val resolver: ControllerResolver, errorHandler: ExceptionMiddleware) : Dispatcher<HttpRequest, HttpResponse> {
    private val middlewares: MutableList<Middleware> = mutableListOf()

    init {
        this.register(object : Middleware {
            override fun intercept(request: HttpRequest, next: Iterator<Middleware>): HttpResponse {
                val handler = resolver.resolve(request);
                return handler.handle(request);
            }
        });
        this.register(errorHandler);
    }

    override fun serve(request: HttpRequest): HttpResponse {
        // TODO: parse http request
        val it = this.middlewares.asReversed().iterator();
        return it.next().intercept(request, it)
    }

    fun register(middleware: Middleware) {
        this.middlewares.add(middleware);
    }
}