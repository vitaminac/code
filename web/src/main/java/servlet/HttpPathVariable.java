package servlet;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface HttpPathVariable {
    /**
     * @return Named Capturing Groups
     */
    String value();
}
