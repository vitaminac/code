package common;

import java.util.List;

public interface Solution<P extends Problem> extends Comparable<Solution<P>> {

    /**
     * @return true if the solution is completed otherwise false
     */
    boolean isSolution();

    /**
     * @return true if the solution is feasible otherwise false
     */
    boolean isFeasible();

    /**
     * @return the list of possible children solutions, empty list if none
     */
    List<Solution<P>> getChildren();
}
