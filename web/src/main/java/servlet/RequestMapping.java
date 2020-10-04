package servlet;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestMapping {
    String path();

    HttpRequest.HttpRequestMethod[] supportedMethods();
}
