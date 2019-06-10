package code.adt.queue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import code.adt.ArrayList;
import code.adt.LinkedList;
import code.adt.Queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class QueueTest {
    private Queue<String> queue;
    private Supplier<Queue<String>> supplier;

    public QueueTest(Supplier<Queue<String>> supplier) {
        this.supplier = supplier;
    }

    @Before
    public void setUp() {
        this.queue = this.supplier.get();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<ArrayList<String>>) ArrayList::new},
                {(Supplier<LinkedList<String>>) LinkedList::new}
        });
    }

    @Test
    public void test() {
        StringBuilder sb = new StringBuilder();
        this.queue.enqueue("to");
        this.queue.enqueue("be");
        this.queue.enqueue("or");
        this.queue.enqueue("not");
        this.queue.enqueue("to");
        sb.append(this.queue.dequeue());
        sb.append(' ');
        this.queue.enqueue("be");
        sb.append(this.queue.dequeue());
        sb.append(' ');
        sb.append(this.queue.dequeue());
        sb.append(' ');
        this.queue.enqueue("that");
        sb.append(this.queue.dequeue());
        sb.append(' ');
        sb.append(this.queue.dequeue());
        sb.append(' ');
        sb.append(this.queue.dequeue());
        this.queue.enqueue("is");
        assertEquals("to be or not to be", sb.toString());
        assertEquals(2, this.queue.size());
    }
}
