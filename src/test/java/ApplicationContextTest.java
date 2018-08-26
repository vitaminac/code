import org.junit.Before;
import org.junit.Test;
import provider.PrototypeProvider;
import provider.SingletonProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ApplicationContextTest {
    private ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        this.context = new ApplicationContext(TestAnnotation.class);
    }

    @Test
    public void getService() throws Exception {
        assertEquals(this.context, this.context.get(ApplicationContext.class));
    }

    @Test
    public void testSingleton() throws Exception {
        this.context.addProvider(TestSingleton.class, new SingletonProvider<>(TestSingleton::new));
        final TestSingleton instance1 = this.context.get(TestSingleton.class);
        final TestSingleton instance2 = this.context.get(TestSingleton.class);
        assertEquals(instance1, instance2);
    }

    @Test
    public void testPrototype() throws Exception {
        this.context.addProvider(TestPrototype.class, new PrototypeProvider<>(() -> new TestPrototype(55)));
        final TestPrototype instance1 = this.context.get(TestPrototype.class);
        final TestPrototype instance2 = this.context.get(TestPrototype.class);
        assertEquals(instance1, instance2);

        instance1.setNumber(1);
        instance2.setNumber(2);
        assertNotEquals(instance1, instance2);
    }

    @Test
    public void testAnnotatedSingleton() throws Exception {
        final TestAnnotatedSingleton instance1 = this.context.get(TestAnnotatedSingleton.class);
        final TestAnnotatedSingleton instance2 = this.context.get(TestAnnotatedSingleton.class);
        assertEquals(instance1, instance2);
    }

    @Test
    public void testAnnotatedPrototype() throws Exception {
        final TestAnnotatedPrototype instance1 = this.context.get(TestAnnotatedPrototype.class);
        final TestAnnotatedPrototype instance2 = this.context.get(TestAnnotatedPrototype.class);
        assertEquals(instance1, instance2);

        instance1.setNumber(1);
        instance2.setNumber(2);
        assertNotEquals(instance1, instance2);
    }
}