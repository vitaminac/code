package code.algorithm.greedy;

public class KnapsackItem implements Comparable<KnapsackItem> {
    private final int id;
    private final double profit;
    private final double weight;

    public KnapsackItem(int id, double profit, double weight) {
        this.id = id;
        this.profit = profit;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public double getProfit() {
        return profit;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(KnapsackItem o) {
        return (this.getProfit() / this.getWeight()) > (o.getProfit() / o.getWeight()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "KnapsackItem{" +
                "id=" + id +
                ", profit=" + profit +
                ", weight=" + weight +
                '}';
    }
}
