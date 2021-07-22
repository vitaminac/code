package core.ioc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationContextTest {
    private static final String SINGLETON_DEPENDENCY_KEY = "SINGLETON KEY";
    private static final String PROTOTYPE_DEPENDENCY_KEY = "PROTOTYPE KEY";
    private static final String STRING_DEPENDENCY = "Value";
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        this.applicationContext = new ApplicationContext();
    }

    public static class Config {
        @Provide(scope = Scope.SINGLETON, name = SINGLETON_DEPENDENCY_KEY)
        public static String singleton() {
            return STRING_DEPENDENCY;
        }

        private static int counter = 0;

        @Provide(scope = Scope.PROTOTYPE, name = PROTOTYPE_DEPENDENCY_KEY)
        public static Integer prototype() {
            return counter++;
        }
    }

    @Test
    public void testSingletonScope() {
        applicationContext.registerConfig(Config.class);
        assertEquals(STRING_DEPENDENCY, applicationContext.resolveDependency(SINGLETON_DEPENDENCY_KEY));
        assertEquals(STRING_DEPENDENCY, applicationContext.resolveDependency(String.class));
    }

    @Test
    public void testPrototypeScope() {
        applicationContext.registerConfig(Config.class);
        assertEquals((Integer) 0, applicationContext.resolveDependency(PROTOTYPE_DEPENDENCY_KEY));
        assertEquals((Integer) 1, applicationContext.resolveDependency(Integer.class));
    }
}