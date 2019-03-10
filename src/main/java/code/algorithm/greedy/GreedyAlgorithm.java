package code.algorithm.greedy;

public abstract class GreedyAlgorithm<Candidate, Solution> {
    private Solution solution;

    public GreedyAlgorithm(Solution solution) {
        this.solution = solution;
    }

    public Solution solve() {
        Candidate best;
        while (!this.isSolution(this.solution)) {
            best = this.select(this.solution);
            if (this.isFeasible(this.solution, best)) {
                this.solution = this.addCandidate(solution, best);
            }
        }
        return this.solution;
    }


    public abstract boolean isFeasible(Solution solution, Candidate candidate);

    public abstract boolean isSolution(Solution solution);

    public abstract Candidate select(Solution solution);

    public abstract Solution addCandidate(Solution solution, Candidate candidate);
}
