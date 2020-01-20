package code.adt.graph;

import java.util.Objects;

public class SimpleEdge implements Edge<Integer> {
    private final int source;
    private final int destination;

    public SimpleEdge(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public Integer getSource() {
        return this.source;
    }

    @Override
    public Integer getDestination() {
        return this.destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof SimpleEdge)) return false;
        SimpleEdge that = (SimpleEdge) o;
        return source == that.source && destination == that.destination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }

    @Override
    public String toString() {
        return "Edge{" + "source=" + this.getSource() + ", destination=" + this.getDestination() + "}";
    }
}
