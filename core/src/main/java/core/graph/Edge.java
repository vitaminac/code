package core.graph;

public interface Edge<Vertex> {
    Vertex getSource();

    Vertex getDestination();
}
