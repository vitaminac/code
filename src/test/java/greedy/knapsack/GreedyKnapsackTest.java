package greedy.knapsack;

import org.junit.Test;

public class GreedyKnapsackTest {
    @Test
    public void test() throws Exception {
        double[] weights = new double[]{10, 20, 30, 40, 50};
        double[] values = new double[]{20, 30, 66, 40, 60};
        final Knapsack knapsack = new Knapsack(values, weights, 100);
        final GreedyKnapsack greedyKnapsack = new GreedyKnapsack(knapsack);
        greedyKnapsack.greedy();
        greedyKnapsack.print();
    }
}