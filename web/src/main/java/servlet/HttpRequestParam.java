package servlet;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface HttpRequestParam {
    /**
     * @return query parameters, form data, and parts in multipart requests
     */
    String value();
}
