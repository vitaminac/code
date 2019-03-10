package code.algorithm.backtracking;

import java.util.Iterator;

public abstract class Backtracking<Solution> {
    public Solution solve(Solution partialSolution) {
        if (this.isSolution(partialSolution)) {
            return partialSolution;
        } else {
            final Iterator<Solution> it = this.expand(partialSolution);
            while (it.hasNext()) {
                final Solution subSolution = it.next();
                if (this.isFeasible(subSolution)) {
                    final Solution subSolutionSolution = this.solve(subSolution);
                    if (subSolutionSolution != null) {
                        return subSolutionSolution;
                    }
                }
            }
        }
        return null;
    }

    public abstract boolean isSolution(Solution solution);

    public abstract Iterator<Solution> expand(Solution partialSolution);

    public abstract boolean isFeasible(Solution partialSolution);
}
