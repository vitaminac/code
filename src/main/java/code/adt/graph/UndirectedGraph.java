package code.adt.graph;

import code.adt.Enumerable;

public interface UndirectedGraph<Vertex, E extends Edge<Vertex>> extends DirectedGraph<Vertex, E> {
    @Override
    default Enumerable<Vertex> getAdjacentVertices(Vertex vertex) {
        return consumer -> this.getEdges(vertex).enumerate(edge -> {
            if (vertex.equals(edge.getSource()))
                consumer.accept(edge.getDestination());
            else
                consumer.accept(edge.getSource());
        });
    }
}
