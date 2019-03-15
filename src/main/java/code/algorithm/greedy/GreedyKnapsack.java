package code.algorithm.greedy;

import code.algorithm.common.Knapsack;
import code.algorithm.common.KnapsackItem;

import java.util.Collection;
import java.util.PriorityQueue;

public class GreedyKnapsack extends GreedyAlgorithm<KnapsackItem, Knapsack> {
    private final PriorityQueue<KnapsackItem> candidates;

    public GreedyKnapsack(double maxWeight, Collection<KnapsackItem> candidates) {
        super(new Knapsack(maxWeight));
        this.candidates = new PriorityQueue<>(candidates);
    }

    @Override
    public boolean isFeasible(Knapsack solution, KnapsackItem knapsackItem) {
        return (solution.getWeight() + knapsackItem.getWeight()) <= solution.getMaxWeight();
    }

    @Override
    public boolean isSolution(Knapsack solution) {
        return solution.getWeight() >= solution.getMaxWeight();
    }


    @Override
    public KnapsackItem select(Knapsack solution) {
        KnapsackItem best = this.candidates.remove();
        if ((best.getWeight() + solution.getWeight()) > (solution.getMaxWeight())) {
            final double remain = solution.getMaxWeight() - solution.getWeight();
            return new KnapsackItem(best.getId(), best.getProfit() * (remain / best.getWeight()), remain);
        } else {
            return best;
        }
    }

    @Override
    public Knapsack addCandidate(Knapsack knapsack, KnapsackItem knapsackItem) {
        knapsack.addCandidate(knapsackItem);
        return knapsack;
    }
}
