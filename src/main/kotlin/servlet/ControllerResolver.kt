package servlet

interface ControllerResolver {
    fun resolve(request: HttpRequest): HttpHandler;
}