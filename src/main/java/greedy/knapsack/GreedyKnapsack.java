package greedy.knapsack;

import java.util.HashSet;
import java.util.Objects;

public class GreedyKnapsack {
    public static class Pair<K, V> {
        private final K key;
        private final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Pair))
                return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(getKey(), pair.getKey()) && Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), value);
        }

        @Override
        public String toString() {
            return "object{" + "key=" + key + ", value=" + value + '}';
        }
    }

    private Knapsack knapsack;
    private HashSet<Pair<Integer, Double>> solution = new HashSet<>();
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

    public void greedyAlgorithmKS() {
        while (this.isFeasible() && !this.candidate.isEmpty()) {
            int best = this.select();
            this.weight += this.knapsack.getWeight(best);
            if (this.isFeasible()) {
                this.totalValue += this.knapsack.getProfit(best);
                this.solution.add(new Pair<>(best, 1.0));
            } else {
                this.weight -= this.knapsack.getWeight(best);
                double frac = (this.knapsack.getMaxWeight() - this.weight) / this.knapsack.getWeight(best);
                this.totalValue += frac * this.knapsack.getProfit(best);
                this.solution.add(new Pair<>(best, frac));
                return;
            }
            this.candidate.remove(best);
        }
    }

    private int select() {
        double bestValueRatio = Double.MIN_VALUE;
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
        for (Pair<Integer, Double> s : this.solution) {
            System.out.println(s.toString() + " weight: " + this.knapsack.getWeight(s.getKey()) + " value: " + this.knapsack.getProfit(s.getKey()));
        }
    }
}
