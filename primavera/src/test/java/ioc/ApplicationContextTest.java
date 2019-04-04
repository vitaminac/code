package ioc;

import org.junit.Before;
import org.junit.Test;
import ioc.provider.Provider;
import ioc.dependency.TestPrototype;
import ioc.dependency.TestSingleton;
import ioc.dependency.TestThreadLocal;

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
        this.context = ApplicationContext.CONTEXT;
    }

    @Test
    public void getService() {
        assertEquals(this.context, this.context.getDependency(Context.class));
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
        assertNotNull(this.context.getDependencyByName("myName"));
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
    public void testConfig() throws Exception {
        // singleton
        final ioc.config.TestSingleton singleton1 = this.context.getDependency(ioc.config.TestSingleton.class);
        final ioc.config.TestSingleton singleton2 = this.context.getDependency(ioc.config.TestSingleton.class);

        assertNotNull(singleton1);
        assertNotNull(singleton2);

        assertEquals(singleton1, singleton2);

        // prototype
        final ioc.config.TestPrototype prototype1 = this.context.getDependency(ioc.config.TestPrototype.class);
        final ioc.config.TestPrototype prototype2 = this.context.getDependency(ioc.config.TestPrototype.class);

        assertNotNull(prototype1);
        assertNotNull(prototype2);

        assertEquals(prototype1, prototype2);

        prototype1.changeState(1);
        prototype2.changeState(2);
        assertNotEquals(prototype1, prototype2);

        // thread
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable testThread = () -> {
            final ioc.config.TestThreadLocal testThreadLocal = this.context.getDependency(ioc.config.TestThreadLocal.class);
            assertEquals(testThreadLocal.getState(), Thread.currentThread().getId());
        };
        final Future<?> future1 = executor.submit(testThread);
        final Future<?> future2 = executor.submit(testThread);

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        future1.get();
        future2.get();

        // named ioc.provider
        assertNotNull(this.context.getDependencyByName("myConfiguredName"));
    }

    @Test
    public void testConstructorInject() {
        assertNotNull(this.context.getDependency(TestConstructorInject.class));
    }

    @Test
    public void testFactoryMethodInject() {
        assertNotNull(this.context.getDependency(TestFactoryMethodInject.class));
    }
}