package code.adt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PriorityQueueTest {
    private PriorityQueue<Double> priorityQueue;
    private Supplier<PriorityQueue<Double>> supplier;

    public PriorityQueueTest(Supplier<PriorityQueue<Double>> supplier) {
        this.supplier = supplier;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<PriorityQueue<Double>>) SortedList::create},
                {(Supplier<PriorityQueue<Double>>) ArrayHeap::create}
        });
    }

    @Before
    public void setUp() {
        this.priorityQueue = this.supplier.get();
        this.priorityQueue.add(15.5);
        this.priorityQueue.add(95.84);
        this.priorityQueue.add(13.68);
        this.priorityQueue.add(10.95);
        this.priorityQueue.add(5.86);
    }

    @Test
    public void add() {
        this.priorityQueue.add(1.1);
        assertEquals(6, this.priorityQueue.size());
        assertFalse(this.priorityQueue.isEmpty());
        assertEquals(1.1, this.priorityQueue.peek(), 0.01);
    }

    @Test
    public void peek() {
        assertEquals(5.86, this.priorityQueue.peek(), 0.01);
    }

    @Test
    public void size() {
        assertEquals(0, SortedList.<Double>create().size());
        assertEquals(5, this.priorityQueue.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(SortedList.<Double>create().isEmpty());
        assertFalse(this.priorityQueue.isEmpty());
    }

    @Test
    public void remove() {
        assertEquals(5.86, this.priorityQueue.remove(), 0.01);
        assertEquals(10.95, this.priorityQueue.remove(), 0.01);
        assertEquals(13.68, this.priorityQueue.remove(), 0.01);
        assertEquals(15.5, this.priorityQueue.remove(), 0.01);
        assertEquals(95.84, this.priorityQueue.remove(), 0.01);
    }

    @Test
    public void iterator() {
        final Iterator<Double> it = this.priorityQueue.iterator();
        assertEquals(5.86, it.next(), 0.001);
        assertEquals(10.95, it.next(), 0.001);
        assertEquals(13.68, it.next(), 0.001);
        assertEquals(15.5, it.next(), 0.001);
        assertEquals(95.84, it.next(), 0.001);
    }
}