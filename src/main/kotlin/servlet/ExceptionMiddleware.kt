package servlet

class ExceptionMiddleware : Middleware {
    override fun intercept(request: HttpRequest, it: Iterator<Middleware>): HttpResponse {
        try {
            return it.next().intercept(request, it);
        } catch (e: Exception) {
            TODO("return ioc.error body");
        }
    }
}