package servlet

interface ViewResolver {
    fun resolve(request: HttpRequest): HttpHandler;
}