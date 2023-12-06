package collections.queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import collections.deque.ArrayDeque;
import collections.deque.LinkedListDeque;
import collections.deque.LinkedListSteque;
import collections.linkedlist.DoublyLinkedList;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class QueueTest {
    private Queue<String> queue;
    private final Supplier<Queue<String>> supplier;

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
                {(Supplier<Queue<String>>) () -> Queue.fromDeque(() -> new ArrayDeque<>(5))},
                {(Supplier<Queue<String>>) () -> Queue.fromDeque(() -> new LinkedListDeque<>(DoublyLinkedList::new))},
                {(Supplier<Queue<String>>) () -> Queue.fromSteque(() -> new LinkedListSteque<>(SinglyLinkedListDoubleReference::new))}
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
