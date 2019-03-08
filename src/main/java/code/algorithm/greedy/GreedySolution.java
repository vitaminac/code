package code.algorithm.greedy;

import java.util.Set;

public interface GreedySolution<Candidate, Objective extends Comparable<Objective>> {
    void addCandidate(Candidate candidate);

    Objective estimate(Set<Candidate> solution);
}
