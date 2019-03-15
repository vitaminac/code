package code.algorithm.greedy;

import code.algorithm.common.Knapsack;
import code.algorithm.common.KnapsackItem;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class GreedyKnapsackTest {
    @Test
    public void test() throws Exception {
        KnapsackItem[] items = new KnapsackItem[]{
                new KnapsackItem(1, 20, 10),
                new KnapsackItem(2, 30, 20),
                new KnapsackItem(3, 66, 30),
                new KnapsackItem(4, 40, 40),
                new KnapsackItem(5, 60, 50),
        };
        final GreedyKnapsack knapsack = new GreedyKnapsack(100, Arrays.asList(items));
        final Knapsack solution = knapsack.solve();
        assertEquals(100, solution.getWeight(), 0.0001);
        assertEquals(115, solution.getProfit(), 0.0001);
    }
}