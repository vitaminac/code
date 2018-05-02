package divideconquer;

import common.Problem;
import common.Solution;

import java.util.List;

public interface ComplexSolver<P extends Problem> {
    List<P> decompose(P problem);

    Solution<P> combine(List<Solution<P>> subSolutions);
}
