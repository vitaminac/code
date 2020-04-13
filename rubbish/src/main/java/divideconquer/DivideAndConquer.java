package divideconquer;

import common.Problem;
import common.Solution;

import java.util.List;
import java.util.stream.Collectors;

public class DivideAndConquer<P extends Problem> {
    private final P problem;
    private final SimpleSolver<P> simpleSolver;
    private final ComplexSolver<P> complexSolver;

    public DivideAndConquer(P problem, SimpleSolver<P> simpleSolver, ComplexSolver<P> complexSolver) {
        this.problem = problem;
        this.simpleSolver = simpleSolver;
        this.complexSolver = complexSolver;
    }

    private Solution<P> solve(P problem) {
        if (this.simpleSolver.isSimpleProblem(problem)) {
            return this.simpleSolver.solve(problem);
        } else {
            final List<P> subProblems = this.complexSolver.decompose(problem);
            final List<Solution<P>> subSolutions = subProblems.stream().map(this::solve).collect(Collectors.toList());
            return complexSolver.combine(subSolutions);
        }
    }

    public Solution<P> solve() {
        return this.solve(this.problem);
    }
}
