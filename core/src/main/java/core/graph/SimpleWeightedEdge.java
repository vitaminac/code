package core.graph;

import java.util.Comparator;

public class SimpleWeightedEdge extends SimpleEdge implements WeightedEdge<Integer, SimpleWeightedEdge> {
    private static Comparator<SimpleWeightedEdge> comparator =
            Comparator.comparingDouble(SimpleWeightedEdge::getWeight)
                    .thenComparingInt(SimpleEdge::getSource)
                    .thenComparingInt(SimpleEdge::getDestination);

    private double weight;

    public SimpleWeightedEdge(int source, int destination, double weight) {
        super(source, destination);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(SimpleWeightedEdge o) {
        return comparator.compare(this, o);
    }

    @Override
    public String toString() {
        return "Edge{" + "source=" + this.getSource() + ", destination=" + this.getDestination() + ", weight=" + this.getWeight() + "}";
    }
}
