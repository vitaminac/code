package servlet

class ExceptionMiddleware : Middleware {
    override fun intercept(request: HttpRequest, next: HttpHandler): HttpResponse {
        try {
            return next.handle(request);
        } catch (e: Exception) {
            TODO("return error body");
        }
    }
}