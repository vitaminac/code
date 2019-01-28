package common.knapsack;

import common.Problem;

public class KnapsackProblem implements Problem {
    private final double profits[];
    private final double weights[];
    private final double maxWeight;

    public KnapsackProblem(double[] profits, double[] weights, double maxWeight) {
        this.profits = profits;
        this.weights = weights;
        this.maxWeight = maxWeight;
    }

    public int size() {
        return this.profits.length;
    }

    public double getProfit(int i) {
        return this.profits[i];
    }

    public double getWeight(int i) {
        return this.weights[i];
    }

    public double getMaxWeight() {
        return this.maxWeight;
    }
}
