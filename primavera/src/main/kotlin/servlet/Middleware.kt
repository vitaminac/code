package servlet

interface Middleware {
    fun intercept(request: HttpRequest, it: Iterator<Middleware>): HttpResponse;
}