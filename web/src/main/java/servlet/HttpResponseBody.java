package servlet;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface HttpResponseBody {
    Class<? extends HttpResponseBodyEncoder<?>> encoder();
}
