package code.algorithm.greedy;

import java.util.Collection;
import java.util.PriorityQueue;

public class GreedyKnapsack extends GreedyAlgorithm<KnapsackItem, Double, Knapsack> {
    private final double maxWeight;
    private final PriorityQueue<KnapsackItem> candidates;

    public GreedyKnapsack(double maxWeight, Collection<KnapsackItem> candidates) {
        super(new Knapsack());
        this.maxWeight = maxWeight;
        this.candidates = new PriorityQueue<>(candidates);
    }

    @Override
    public boolean isFeasible(Knapsack solution, KnapsackItem knapsackItem) {
        return (solution.getWeight() + knapsackItem.getWeight()) <= this.maxWeight;
    }

    @Override
    public boolean isSolution(Knapsack solution) {
        return solution.getWeight() >= this.maxWeight;
    }

    @Override
    public boolean hasMoreCandidate() {
        return !this.candidates.isEmpty();
    }

    @Override
    public KnapsackItem select(Knapsack solution) {
        KnapsackItem best = this.candidates.remove();
        if ((best.getWeight() + solution.getWeight()) > (this.maxWeight)) {
            final double remain = this.maxWeight - solution.getWeight();
            return new KnapsackItem(best.getId(), best.getProfit() * (remain / best.getWeight()), remain);
        } else {
            return best;
        }
    }
}
