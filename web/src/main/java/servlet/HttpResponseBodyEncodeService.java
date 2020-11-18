package servlet;

public interface HttpResponseBodyEncodeService {
    HttpResponse encode(Object o, HttpResponseBody annotation);
}
