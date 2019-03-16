package code.adt;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SortedListTest {
    private SortedList<Double> sortedList;

    @Before
    public void setUp() throws Exception {
        this.sortedList = SortedList.create();
        this.sortedList.add(15.5);
        this.sortedList.add(95.84);
        this.sortedList.add(13.68);
        this.sortedList.add(10.95);
        this.sortedList.add(5.86);
    }

    @Test
    public void add() {
        this.sortedList.add(1.1);
        assertEquals(6, this.sortedList.size());
        assertFalse(this.sortedList.isEmpty());
        assertEquals(1.1, this.sortedList.peek(), 0.01);
    }

    @Test
    public void peek() {
        assertEquals(5.86, this.sortedList.peek(), 0.01);
    }

    @Test
    public void size() {
        assertEquals(0, SortedList.<Double>create().size());
        assertEquals(5, this.sortedList.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(SortedList.<Double>create().isEmpty());
        assertFalse(this.sortedList.isEmpty());
    }

    @Test
    public void remove() {
        assertEquals(5.86, this.sortedList.peek(), 0.01);
    }

    @Test
    public void iterator() {
        final Iterator<Double> it = this.sortedList.iterator();
        assertEquals(5.86, it.next(), 0.001);
        assertEquals(10.95, it.next(), 0.001);
        assertEquals(13.68, it.next(), 0.001);
        assertEquals(15.5, it.next(), 0.001);
        assertEquals(95.84, it.next(), 0.001);
    }
}