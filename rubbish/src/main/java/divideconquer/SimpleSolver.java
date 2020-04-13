package divideconquer;

import common.Problem;
import common.Solution;

public interface SimpleSolver<P extends Problem> {
    boolean isSimpleProblem(P problem);

    Solution<P> solve(P problem);
}
