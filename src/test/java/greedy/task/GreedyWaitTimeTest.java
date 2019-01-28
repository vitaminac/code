package greedy.task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreedyWaitTimeTest {
    @Test
    public void test() {
        final Task[] tasks = new Task[]{new Task(5), new Task(10), new Task(3)};
        final GreedyWaitTime greedyWaitTime = new GreedyWaitTime(tasks);
        greedyWaitTime.arrange();
        assertEquals(29, greedyWaitTime.getTotalWaitTime(), 0.0000001);
    }
}