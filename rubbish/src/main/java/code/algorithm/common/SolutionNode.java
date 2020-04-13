package code.algorithm.common;

import core.Queue;

public interface SolutionNode<Solution extends SolutionNode<Solution>> {
    boolean isSolution();

    boolean isFeasible();

    Queue<Solution> branch();

    void backtrack();
}
