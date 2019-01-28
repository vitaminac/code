package common.knapsack;

import common.Solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class KnapsackSolution implements Solution<KnapsackProblem> {
    private final KnapsackProblem knapsackProblem;
    private final PriorityQueue<Candidate> candidates = new PriorityQueue<>();
    private final HashSet<Integer> selectedItems = new HashSet<>();
    private final double currentWeight;
    private final double currentProfit;

    public KnapsackSolution(KnapsackProblem knapsackProblem) {
        this.knapsackProblem = knapsackProblem;
        this.currentWeight = 0;
        this.currentProfit = 0;
        for (int i = 0; i < this.knapsackProblem.size(); i++) {
            this.candidates.add(new Candidate(i, this.knapsackProblem.getProfit(i) / this.knapsackProblem.getWeight(i)));
        }
    }

    public KnapsackSolution(KnapsackProblem knapsackProblem, int selectedItems[]) {
        this.knapsackProblem = knapsackProblem;
        double weight = 0, profit = 0;
        for (int item : selectedItems) {
            this.selectedItems.add(item);
            weight += this.knapsackProblem.getWeight(item);
            profit += this.knapsackProblem.getProfit(item);
        }
        this.currentWeight = weight;
        this.currentProfit = profit;
    }

    public KnapsackSolution(KnapsackSolution knapsackSolution, double currentWeight, double currentProfit) {
        this.knapsackProblem = knapsackSolution.knapsackProblem;
        this.candidates.addAll(knapsackSolution.candidates);
        this.selectedItems.addAll(knapsackSolution.selectedItems);
        this.currentWeight = currentWeight;
        this.currentProfit = currentProfit;
    }

    @Override
    public boolean isSolution() {
        return this.candidates.isEmpty();
    }

    @Override
    public boolean isFeasible() {
        return this.currentWeight <= this.knapsackProblem.getMaxWeight();
    }

    @Override
    public List<Solution<KnapsackProblem>> getChildren() {
        ArrayList<Solution<KnapsackProblem>> children = new ArrayList<>();
        final int candidate = this.candidates.remove().candidate;
        this.selectedItems.add(candidate);
        children.add(new KnapsackSolution(this, this.currentWeight + this.knapsackProblem.getWeight(candidate), this.currentProfit + this.knapsackProblem.getProfit(candidate)));
        this.selectedItems.remove(candidate);
        children.add(new KnapsackSolution(this, this.currentWeight, this.currentProfit));
        return children;
    }

    private double getEstimatedValue() {
        double best = 0;
        if (!this.isSolution()) {
            return this.currentProfit + (this.knapsackProblem.getMaxWeight() - this.currentWeight) * this.candidates.peek().score;
        } else {
            return this.currentProfit;
        }
    }

    @Override
    public int compareTo(Solution<KnapsackProblem> o) {
        final KnapsackSolution sol = (KnapsackSolution) o;
        return (this.getEstimatedValue() - ((KnapsackSolution) o).getEstimatedValue()) > 0 ? -1 : 1;
    }

    @Override
    public String toString() {
        return "KnapsackSolution{" + "currentWeight=" + currentWeight + ", currentProfit=" + currentProfit + ", estimatedValue="+this.getEstimatedValue()+'}';
    }

    private class Candidate implements Comparable<Candidate> {
        private final int candidate;
        private final double score;

        public Candidate(int candidate, double score) {
            this.candidate = candidate;
            this.score = score;
        }

        @Override
        public int compareTo(Candidate o) {
            return (this.score - o.score) > 0 ? -1 : 1;
        }
    }
}
