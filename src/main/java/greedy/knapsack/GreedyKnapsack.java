package greedy.knapsack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GreedyKnapsack {

    private Knapsack knapsack;
    private HashMap<Integer, Double> solution = new HashMap<>();
    private HashSet<Integer> candidate = new HashSet<>();
    private double weight = 0;
    private double totalValue = 0;

    public GreedyKnapsack(Knapsack k) {
        this.knapsack = k;
        for (int i = this.knapsack.size() - 1; i >= 0; i--) {
            this.candidate.add(i);
        }
    }

    private boolean isFeasible() {
        return this.weight <= this.knapsack.getMaxWeight();
    }

    public void greedy() {
        while (!this.candidate.isEmpty() && !this.isSolution()) {
            int best = this.select();
            this.weight += this.knapsack.getWeight(best);
            if (this.isFeasible()) {
                this.totalValue += this.knapsack.getProfit(best);
                this.solution.put(best, 1.0);
            } else {
                this.weight -= this.knapsack.getWeight(best);
                double frac = (this.knapsack.getMaxWeight() - this.weight) / this.knapsack.getWeight(best);
                this.totalValue += frac * this.knapsack.getProfit(best);
                this.solution.put(best, frac);
                return;
            }
            this.candidate.remove(best);
        }
    }

    private boolean isSolution() {
        return this.weight == knapsack.getMaxWeight();
    }

    private int select() {
        double bestValueRatio = 0;
        int best = 0;
        for (int cand : this.candidate) {
            if ((this.knapsack.getProfit(cand) / this.knapsack.getWeight(cand) > bestValueRatio)) {
                best = cand;
                bestValueRatio = this.knapsack.getProfit(best) / this.knapsack.getWeight(best);
            }
        }
        return best;
    }

    public void print() {
        for (Map.Entry<Integer, Double> entry : this.solution.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue() + " weight: " + this.knapsack.getWeight(entry.getKey()) + " value: " + this.knapsack.getProfit(entry.getKey()));
        }
        System.out.println("total value: " + this.totalValue);
    }
}
