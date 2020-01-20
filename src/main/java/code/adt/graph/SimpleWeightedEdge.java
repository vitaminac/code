package code.adt.graph;

public class SimpleWeightedEdge extends SimpleEdge implements WeightedEdge<Integer, SimpleWeightedEdge> {
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
        return (this.weight - o.weight) > 0 ? 1 : -1;
    }

    @Override
    public String toString() {
        return "Edge{" + "source=" + this.getSource() + ", destination=" + this.getDestination() + ", weight=" + this.getWeight() + "}";
    }
}
