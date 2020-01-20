package code.adt.graph;

import code.adt.Enumerable;

import java.util.HashSet;
import java.util.Set;

public class SimpleDirectedGraph<E extends Edge<Integer>> implements Graph<Integer, E> {
    protected Set<E>[] g;

    public SimpleDirectedGraph(int n) {
        this.g = (Set<E>[]) new Set[n];
        for (int i = 0; i < n; i++) {
            this.g[i] = new HashSet<>();
        }
    }

    public int size() {
        return this.g.length;
    }

    @Override
    public boolean isAdjacent(Integer u, Integer v) {
        return this.getAdjacentVertices(u).some(vertex -> vertex.equals(v));
    }

    @Override
    public Enumerable<E> getEdges(Integer u) {
        return consumer -> {
            for (var edge : this.g[u]) {
                consumer.accept(edge);
            }
        };
    }

    @Override
    public Enumerable<Integer> getAdjacentVertices(Integer vertex) {
        return consumer -> this.getEdges(vertex).enumerate(edge -> consumer.accept(edge.getDestination()));
    }

    @Override
    public void addEdge(E edge) {
        this.g[edge.getSource()].add(edge);
    }
}
