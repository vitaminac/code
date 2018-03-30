package greedy.graph;

import java.util.Objects;

public class Edge implements Comparable<Edge> {

    private int source;
    private int destination;
    private double weight;

    public Edge(int origin, int destination, double weight) {
        this.source = origin;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return source == edge.source &&
                destination == edge.destination &&
                Double.compare(edge.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, weight);
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge o) {
        return (this.weight - o.weight) > 1 ? 1 : -1;
    }
}
