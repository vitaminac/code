package code.algorithm.common;

import code.adt.Queue;

public interface SolutionNode<Solution extends SolutionNode<Solution>> {
    boolean isSolution();

    boolean isFeasible();

    Queue<Solution> expand();

    void backtrack();
}
