package collections.heap;

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
public class HeapTest {
    private Heap<Double> heap;
    private Supplier<Heap<Double>> supplier;

    public HeapTest(Supplier<Heap<Double>> supplier) {
        this.supplier = supplier;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<Heap<Double>>) ArrayHeap::create}
        });
    }

    @Before
    public void setUp() {
        this.heap = this.supplier.get();
        this.heap.add(15.5);
        this.heap.add(95.84);
        this.heap.add(13.68);
        this.heap.add(10.95);
        this.heap.add(5.86);
    }

    @Test
    public void add() {
        this.heap.add(1.1);
        assertEquals(6, this.heap.size());
        assertFalse(this.heap.isEmpty());
        assertEquals(1.1, this.heap.min(), 0.01);
    }

    @Test
    public void peek() {
        assertEquals(5.86, this.heap.min(), 0.01);
    }

    @Test
    public void size() {
        assertEquals(5, this.heap.size());
    }

    @Test
    public void isEmpty() {
        assertFalse(this.heap.isEmpty());
    }

    @Test
    public void remove() {
        assertEquals(5.86, this.heap.removeMin(), 0.01);
        assertEquals(10.95, this.heap.removeMin(), 0.01);
        assertEquals(13.68, this.heap.removeMin(), 0.01);
        assertEquals(15.5, this.heap.removeMin(), 0.01);
        assertEquals(95.84, this.heap.removeMin(), 0.01);
    }
}