package servlet

interface Middleware {
    fun intercept(request: HttpRequest, next: HttpHandler): HttpResponse;
}