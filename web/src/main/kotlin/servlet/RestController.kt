package servlet

abstract class RestController<DTO> : HttpHandler {
    val converter: DTOConverter<DTO> = DTOConverter();
    override fun handle(request: HttpRequest): HttpResponse {
        when (request.method) {
            HttpMethod.GET -> this.doGet(request);
            HttpMethod.POST -> this.doPost(this.converter.transform(request))
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun doGet(request: HttpRequest): DTO;

    abstract fun doPost(dto: DTO): HttpResponse;
}