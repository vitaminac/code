package core.graph;

import core.Enumerable;

public class AbstractUndirectedGraph<E extends Edge<Integer>> extends AbstractDirectedGraph<E> implements UndirectedGraph<Integer, E> {
    public AbstractUndirectedGraph(int n) {
        super(n);
    }

    @Override
    public void addEdge(E edge) {
        this.g[edge.getSource()].add(edge);
        this.g[edge.getDestination()].add(edge);
    }

    @Override
    public Enumerable<Integer> getAdjacentVertices(Integer vertex) {
        return UndirectedGraph.super.getAdjacentVertices(vertex);
    }
}
