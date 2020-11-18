package servlet;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface HttpRequestBody {
    Class<? extends HttpRequestBodyDecoder<?>> decoder();
}
