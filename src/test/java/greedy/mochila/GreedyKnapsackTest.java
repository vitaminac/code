package greedy.mochila;

import greedy.mochila.GreedyKnapsack.Pair;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class GreedyKnapsackTest {
    private void testGreedyAlgorithmKS(double[] weights, double[] profits, int n, double maxWeight, HashSet<Pair<Integer, Double>> sol) throws Exception {
        final Knapsack knapsack = new Knapsack(10);
        final Class<? extends Knapsack> knapsackC = knapsack.getClass();
        final Field profit = knapsackC.getDeclaredField("profit");
        final Field weight = knapsackC.getDeclaredField("weight");
        final Field maxWeightF = knapsackC.getDeclaredField("maxWeight");
        profit.setAccessible(true);
        weight.setAccessible(true);
        maxWeightF.setAccessible(true);
        profit.set(knapsack, profits);
        weight.set(knapsack, weights);
        maxWeightF.set(knapsack, maxWeight);
        final GreedyKnapsack greedyKnapsack = new GreedyKnapsack(knapsack);
        greedyKnapsack.greedyAlgorithmKS();
        final Field solution = greedyKnapsack.getClass().getDeclaredField("solution");
        solution.setAccessible(true);
        greedyKnapsack.print();
        assertEquals(sol, solution.get(greedyKnapsack));
    }

    @Test
    public void greedyAlgorithmKS() throws Exception {
        double[] weights = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] values = new double[]{2, 4, 6, 8, 6, 5, 4, 3, 2, 1};
        HashSet<Pair<Integer, Double>> sol = new HashSet<>();
        sol.add(new Pair<>(0, 1.0));
        sol.add(new Pair<>(1, 1.0));
        sol.add(new Pair<>(2, 1.0));
        sol.add(new Pair<>(3, 0.5));
        this.testGreedyAlgorithmKS(weights, values, 10, 8, sol);
    }

    @Test
    public void defaultTest() throws Exception {
        double[] weights = new double[]{10, 20, 30, 40, 50};
        double[] values = new double[]{20, 30, 66, 40, 60};
        final HashSet<Pair<Integer, Double>> sols = new HashSet<>();
        sols.add(new Pair<>(0, 1.0));
        sols.add(new Pair<>(1, 1.0));
        sols.add(new Pair<>(2, 1.0));
        sols.add(new Pair<>(4, 0.8));
        this.testGreedyAlgorithmKS(weights, values, 5, 100, sols);
    }
}