package branchbound;

import common.Problem;
import common.Solution;

import java.util.PriorityQueue;

/**
 * solve with the branch and bound algorithm
 *
 * @param <P> the discrete and combinatorial optimization problem
 */
public class DiscreteOptimizer<P extends Problem> {
    private Solution<P> solution;
    private PriorityQueue<Solution<P>> partialSolutions = new PriorityQueue<>();

    public DiscreteOptimizer(Solution<P> up, Solution<P> initialPartialSolution) {
        this.solution = up;
        this.partialSolutions.add(initialPartialSolution);
    }

    private void branch(Solution<P> partialSol) {
        // discard if it cannot produce a better solution
        if (partialSol.compareTo(this.solution) < 0) {
            for (Solution<P> child : partialSol.getChildren()) {
                if (child.isFeasible() && (child.compareTo(this.solution) < 0)) {
                    this.partialSolutions.add(child);
                }
            }
        }
    }

    private void bound(Solution<P> candidate) {
        if (candidate.compareTo(this.solution) < 0) {
            this.solution = candidate;
        }
    }

    public Solution<P> minimize() {
        while (!this.partialSolutions.isEmpty()) {
            Solution<P> sol = this.partialSolutions.remove();
            if (sol.isSolution()) {
                this.bound(sol);
            } else {
                this.branch(sol);
            }
        }
        return this.solution;
    }
}
