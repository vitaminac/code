package core.queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
                {(Supplier<PriorityQueue<Double>>) ArrayHeap::create}
        });
    }

    @Before
    public void setUp() {
        this.priorityQueue = this.supplier.get();
        this.priorityQueue.enqueue(15.5);
        this.priorityQueue.enqueue(95.84);
        this.priorityQueue.enqueue(13.68);
        this.priorityQueue.enqueue(10.95);
        this.priorityQueue.enqueue(5.86);
    }

    @Test
    public void add() {
        this.priorityQueue.enqueue(1.1);
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
        assertEquals(5, this.priorityQueue.size());
    }

    @Test
    public void isEmpty() {
        assertFalse(this.priorityQueue.isEmpty());
    }

    @Test
    public void remove() {
        assertEquals(5.86, this.priorityQueue.dequeue(), 0.01);
        assertEquals(10.95, this.priorityQueue.dequeue(), 0.01);
        assertEquals(13.68, this.priorityQueue.dequeue(), 0.01);
        assertEquals(15.5, this.priorityQueue.dequeue(), 0.01);
        assertEquals(95.84, this.priorityQueue.dequeue(), 0.01);
    }
}