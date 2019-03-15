package code.algorithm.branchandbound;

import code.algorithm.common.SolutionNode;

import java.util.PriorityQueue;

public class BranchAndBound<Solution extends SolutionNode<Solution> & Comparable<Solution>> {
    public Solution minimize(Solution bound, Solution initialPartialSolution) {
        PriorityQueue<Solution> problemSpace = new PriorityQueue<>();
        problemSpace.add(initialPartialSolution);
        while (!problemSpace.isEmpty()) {
            Solution solution = problemSpace.remove();
            for (Solution child : solution.branch()) {
                if (child.isFeasible() && (child.compareTo(bound) < 0)) {
                    if (solution.isSolution()) {
                        bound = solution;
                    } else {
                        problemSpace.add(child);
                    }
                }
            }
        }
        return bound;
    }
}
