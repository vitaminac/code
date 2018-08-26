import org.junit.Before;
import org.junit.Test;
import provider.PrototypeProvider;
import provider.SingletonProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ApplicationContextTest {
    private ApplicationContext context = new ApplicationContext();

    @Before
    public void setUp() throws Exception {
        context.addProvider(TestSingleton.class, new SingletonProvider<>(TestSingleton::new));
        context.addProvider(TestPrototype.class, new PrototypeProvider<>(TestPrototype::new));
    }

    @Test
    public void getService() throws Exception {
        assertEquals(this.context, this.context.get(ApplicationContext.class));
    }

    @Test
    public void testSingleton() throws Exception {
        final TestSingleton instance1 = this.context.get(TestSingleton.class);
        final TestSingleton instance2 = this.context.get(TestSingleton.class);
        assertEquals(instance1, instance2);
    }

    @Test
    public void testPrototype() throws Exception {
        final TestPrototype instance1 = this.context.get(TestPrototype.class);
        final TestPrototype instance2 = this.context.get(TestPrototype.class);
        assertEquals(instance1, instance2);

        instance1.setNumber(1);
        instance2.setNumber(2);
        assertNotEquals(instance1, instance2);
    }
}