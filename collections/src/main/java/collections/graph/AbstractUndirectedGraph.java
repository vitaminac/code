package collections.graph;

import collections.set.NavigableSet;

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
    public NavigableSet<Integer> getAdjacentVertices(Integer vertex) {
        return UndirectedGraph.super.getAdjacentVertices(vertex);
    }
}
