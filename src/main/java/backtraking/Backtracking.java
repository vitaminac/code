package backtraking;

import common.Problem;
import common.Solution;

import java.util.Set;
import java.util.TreeSet;

public class Backtracking<P extends Problem> {
    private final TreeSet<Solution<P>> solutions = new TreeSet<>();

    public void solve(Solution<P> partialSolution) {
        if (partialSolution.isSolution()) {
            this.solutions.add(partialSolution);
        } else {
            for (Solution<P> child : partialSolution.getChildren()) {
                if (child.isFeasible()) {
                    this.solve(child);
                }
            }
        }
    }

    public Set<Solution<P>> getSolutions() {
        return solutions;
    }
}
