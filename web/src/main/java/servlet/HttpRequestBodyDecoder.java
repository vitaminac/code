package servlet;

public interface HttpRequestBodyDecoder<DTO> {
    DTO decode(HttpRequest request);
}
