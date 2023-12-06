package code.algorithm.backtracking;

import code.algorithm.common.SolutionNode;
import collections.queue.Queue;

public class Backtracking<Solution extends SolutionNode<Solution>> {
    public Solution solve(Solution partialSolution) {
        if (partialSolution.isFeasible()) {
            if (partialSolution.isSolution()) {
                return partialSolution;
            } else {
                final Queue<Solution> candidateSolutionQueue = partialSolution.branch();
                while (!candidateSolutionQueue.isEmpty()) {
                    final Solution solution = this.solve(candidateSolutionQueue.dequeue());
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
