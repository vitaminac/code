package core.functional;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnumerableTest {
    @Test
    public void testIterator() {
        final Enumerable<Integer> enumerable = consumer -> {
            consumer.accept(1);
            consumer.accept(2);
            consumer.accept(3);
            consumer.accept(4);
            consumer.accept(5);
        };
        final var it = enumerable.iterator();
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertEquals(1, (int) it.next());
        assertEquals(2, (int) it.next());
        assertEquals(3, (int) it.next());
        assertTrue(it.hasNext());
        assertEquals(4, (int) it.next());
        assertEquals(5, (int) it.next());
        assertFalse(it.hasNext());
    }
}