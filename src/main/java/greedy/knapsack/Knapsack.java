package greedy.knapsack;

public class Knapsack {

    private double[] profits;
    private double[] weights;
    private double maxWeight;

    public Knapsack(double[] profits, double[] weights, double maxWeight) {
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
