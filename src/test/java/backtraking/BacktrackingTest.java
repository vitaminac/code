package backtraking;

import common.Solution;
import common.knapsack.KnapsackProblem;
import common.knapsack.KnapsackSolution;
import org.junit.Test;

import java.util.TreeSet;

public class BacktrackingTest {

    @Test
    public void solve() {
        final KnapsackProblem knapsackProblem = new KnapsackProblem(new double[]{10, 10, 12, 18}, new double[]{2, 4, 6, 9}, 15);
        Solution<KnapsackProblem> initialSol = new KnapsackSolution(knapsackProblem);
        final Backtracking<KnapsackProblem> knapsackProblemBacktracking = new Backtracking<>();
        knapsackProblemBacktracking.solve(initialSol);
        System.out.println(knapsackProblemBacktracking.getSolutions());
    }
}