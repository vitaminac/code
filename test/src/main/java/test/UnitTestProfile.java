package test;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UnitTestProfile {
    long timeoutInNanoseconds() default Long.MAX_VALUE;
}
