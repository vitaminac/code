package code.adt.graph;

public interface Edge<Vertex> {
    Vertex getSource();

    Vertex getDestination();
}
