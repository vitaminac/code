package code.algorithm.greedy;

import java.util.HashSet;
import java.util.Set;

public class Knapsack {
    private Set<KnapsackItem> items = new HashSet<>();
    private double profit = 0;
    private double weight = 0;
    
    public void addCandidate(KnapsackItem knapsackItem) {
        this.items.add(knapsackItem);
        this.profit += knapsackItem.getProfit();
        this.weight += knapsackItem.getWeight();
    }

    public double getProfit() {
        return profit;
    }

    public double getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "Knapsack{" +
                "items=" + items +
                ", profit=" + profit +
                ", weight=" + weight +
                '}';
    }
}
