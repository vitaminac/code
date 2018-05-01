package branchbound;

import common.Solution;
import common.knapsack.KnapsackProblem;
import common.knapsack.KnapsackSolution;
import org.junit.Test;

public class DiscreteOptimizerTest {

    @Test
    public void minimize() {
        final KnapsackProblem knapsackProblem = new KnapsackProblem(new double[]{10, 10, 12, 18}, new double[]{2, 4, 6, 9}, 15);
        Solution<KnapsackProblem> initialSol = new KnapsackSolution(knapsackProblem, new int[]{0, 1, 2});
        final DiscreteOptimizer<KnapsackProblem> optimizer = new DiscreteOptimizer<>(initialSol, new KnapsackSolution(knapsackProblem));
        System.out.println(optimizer.minimize());
    }
}