package code.algorithm.greedy;

public abstract class GreedyAlgorithm<Candidate, Objective extends Comparable<Objective>, Solution extends GreedySolution<Candidate, Objective>> {
    private final Solution solution;

    public GreedyAlgorithm(Solution solution) {
        this.solution = solution;
    }

    public Solution solve() {
        Candidate best;
        while (this.hasMoreCandidate() && !this.isSolution(this.solution)) {
            best = this.select(this.solution);
            if (this.isFeasible(this.solution, best)) {
                this.solution.addCandidate(best);
            }
        }
        return this.solution;
    }


    public abstract boolean isFeasible(Solution solution, Candidate candidate);

    public abstract boolean isSolution(Solution solution);

    public abstract boolean hasMoreCandidate();

    public abstract Candidate select(Solution solution);
}
