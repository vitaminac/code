import org.junit.Before;
import org.junit.Test;
import provider.Provider;
import provider.TestPrototype;
import provider.TestSingleton;
import provider.TestThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ApplicationContextTest {
    private Context context;

    @Before
    public void setUp() {
        this.context = ApplicationContext.load();
    }

    @Test
    public void getService() {
        assertEquals(this.context, this.context.getDependency(ApplicationContext.class));
    }

    @Test
    public void testSingleton() {
        final TestSingleton instance1 = this.context.getDependency(TestSingleton.class);
        final TestSingleton instance2 = this.context.getDependency(TestSingleton.class);

        assertNotNull(instance1);
        assertNotNull(instance2);

        assertEquals(instance1, instance2);
    }

    @Test
    public void testPrototype() {
        final TestPrototype instance1 = this.context.getDependency(TestPrototype.class);
        final TestPrototype instance2 = this.context.getDependency(TestPrototype.class);

        assertNotNull(instance1);
        assertNotNull(instance2);

        assertEquals(instance1, instance2);

        instance1.changeState(1);
        instance2.changeState(2);
        assertNotEquals(instance1, instance2);
    }

    @Test
    public void testGetDependencyByName() {
        // TODO
    }

    @Test
    public void testCustomProvider() {
        Provider<Integer> provider = new Provider<Integer>() {
            private int seed = 0;

            @Override
            public Integer provide() {
                return seed++;
            }
        };
        String name = "myCustomDependecy";
        this.context.registerProvider(name, provider);
        assertEquals(Integer.valueOf(0), this.context.getDependencyByName(name));
        assertEquals(Integer.valueOf(1), this.context.getDependencyByName(name));
        assertEquals(Integer.valueOf(2), this.context.getDependencyByName(name));
        assertEquals(Integer.valueOf(3), this.context.getDependencyByName(name));
        assertEquals(Integer.valueOf(4), this.context.getDependencyByName(name));
        assertEquals(Integer.valueOf(5), this.context.getDependencyByName(name));
    }

    @Test
    public void testThreadLocal() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable testThread = () -> {
            final TestThreadLocal testThreadLocal = this.context.getDependency(TestThreadLocal.class);
            assertEquals(testThreadLocal.getState(), Thread.currentThread().getId());
        };
        final Future<?> future1 = executor.submit(testThread);
        final Future<?> future2 = executor.submit(testThread);

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        future1.get();
        future2.get();
    }

    @Test
    public void testConfig() {
        // singleton
        final TestAnnotatedSingleton singleton1 = this.context.getDependency(TestAnnotatedSingleton.class);
        final TestAnnotatedSingleton singleton2 = this.context.getDependency(TestAnnotatedSingleton.class);
        assertNotNull(singleton1);
        assertNotNull(singleton2);
        assertEquals(singleton1, singleton2);

        // prototype
        final TestAnnotatedPrototype prototype1 = this.context.getDependency(TestAnnotatedPrototype.class);
        final TestAnnotatedPrototype prototype2 = this.context.getDependency(TestAnnotatedPrototype.class);
        assertNotNull(prototype1);
        assertNotNull(prototype2);
        assertEquals(prototype1, prototype2);
        prototype1.setNumber(1);
        prototype2.setNumber(2);
        assertNotEquals(prototype1, prototype2);
    }

    @Test
    public void testAnnotatedSingletonMore() {
        final TestAnnotatedSingletonMore instance1 = this.context.getDependency(TestAnnotatedSingletonMore.class);
        final TestAnnotatedSingletonMore instance2 = this.context.getDependency(TestAnnotatedSingletonMore.class);
        assertEquals(instance1, instance2);
    }

    @Test
    public void testAnnotatedPrototypeMore() {
        final TestAnnotatedPrototypeMore instance1 = this.context.getDependency(TestAnnotatedPrototypeMore.class);
        final TestAnnotatedPrototypeMore instance2 = this.context.getDependency(TestAnnotatedPrototypeMore.class);
        assertEquals(instance1, instance2);

        instance1.setNumber(1);
        instance2.setNumber(2);
        assertNotEquals(instance1, instance2);
    }

    @Test
    public void testClassLevelAnnotation() {
        final TestClassLevelAnnotation instance1 = this.context.getDependency(TestClassLevelAnnotation.class);
        final TestClassLevelAnnotation instance2 = this.context.getDependency(TestClassLevelAnnotation.class);
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertEquals(instance1, instance2);
    }
}