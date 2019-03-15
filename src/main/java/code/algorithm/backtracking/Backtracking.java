package code.algorithm.backtracking;

import code.algorithm.common.SolutionNode;

public class Backtracking<Solution extends SolutionNode<Solution>> {
    public Solution solve(Solution partialSolution) {
        if (partialSolution.isFeasible()) {
            if (partialSolution.isSolution()) {
                return partialSolution;
            } else {
                for (Solution sol : partialSolution.expand()) {
                    final Solution solution = this.solve(sol);
                    if (solution != null) {
                        return solution;
                    }
                }
            }
        }
        partialSolution.backtrack();
        return null;
    }
}
