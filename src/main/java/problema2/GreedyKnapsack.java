package problema2;

import java.util.PriorityQueue;

public class GreedyKnapsack {

    private Data data;
    private double totalProfit;

    public GreedyKnapsack(Data k) {
        this.data = k;
    }

    public void greedyAlgorithmKS() {
        this.totalProfit = 0;
        double freeWeight = this.data.getMaxWeight();
        PriorityQueue<Candidate> candidates = new PriorityQueue<>();
        for (int i = 0; i < this.data.size(); i++) {
            candidates.add(new Candidate(i, this.data.getProfit(i) / this.data.getWeight(i)));
        }
        while (!candidates.isEmpty() && freeWeight > 0) {
            // select
            final Candidate candidate = candidates.remove();
            if (this.data.getWeight(candidate.object) < freeWeight) {
                this.totalProfit += this.data.getProfit(candidate.object);
                freeWeight -= this.data.getWeight(candidate.object);
            } else {
                this.totalProfit += candidate.ratio * freeWeight;
                freeWeight = 0;
            }
        }
    }

    public double getSumProfit() {
        return totalProfit;
    }

    private class Candidate implements Comparable<Candidate> {
        private final int object;
        private final double ratio;

        private Candidate(int object, double ratio) {
            this.object = object;
            this.ratio = ratio;
        }

        @Override
        public int compareTo(Candidate o) {
            return this.ratio > o.ratio ? -1 : 1;
        }
    }
}
