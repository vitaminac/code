package servlet;

public interface HttpResponseBodyEncoder<DTO> {
    HttpResponse encode(DTO dto);
}
