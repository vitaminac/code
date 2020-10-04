package ioc.injection;

import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Inject {
}
