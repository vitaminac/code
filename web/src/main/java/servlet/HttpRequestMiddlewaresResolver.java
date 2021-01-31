package servlet;

import java.util.List;

public interface HttpRequestMiddlewaresResolver {
    List<HttpRequestMiddleware> resolve();
}
