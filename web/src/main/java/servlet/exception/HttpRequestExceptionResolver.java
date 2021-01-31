package servlet.exception;

import servlet.HttpResponse;

public interface HttpRequestExceptionResolver {
    HttpResponse resolve(Exception e);
}
