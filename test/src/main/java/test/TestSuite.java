package test;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TestSuite {
    Class<? extends UnitTestRunner> runWith() default DefaultUnitTestRunner.class;
}
