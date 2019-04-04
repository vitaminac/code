package servlet

interface HttpHandler {
    fun handle(request: HttpRequest): HttpResponse;
}