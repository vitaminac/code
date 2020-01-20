package code.adt.graph;

import code.adt.Enumerable;

public class SimpleUndirectedGraph<E extends Edge<Integer>> extends SimpleDirectedGraph<E> {
    public SimpleUndirectedGraph(int n) {
        super(n);
    }

    @Override
    public Enumerable<Integer> getAdjacentVertices(Integer vertex) {
        return consumer -> this.getEdges(vertex).enumerate(edge -> {
            if (vertex.equals(edge.getSource()))
                consumer.accept(edge.getDestination());
            else
                consumer.accept(edge.getSource());
        });
    }

    @Override
    public void addEdge(E edge) {
        this.g[edge.getSource()].add(edge);
        this.g[edge.getDestination()].add(edge);
    }
}
