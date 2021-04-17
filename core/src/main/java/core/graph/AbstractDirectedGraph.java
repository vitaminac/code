package core.graph;

import core.functional.Enumerable;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDirectedGraph<E extends Edge<Integer>> implements DirectedGraph<Integer, E> {
    protected Set<E>[] g;

    public AbstractDirectedGraph(int n) {
        this.g = (Set<E>[]) new Set[n];
        for (int i = 0; i < n; i++) {
            this.g[i] = new HashSet<>();
        }
    }

    public int size() {
        return g.length;
    }

    @Override
    public Enumerable<Integer> getVertices() {
        return consumer -> {
            for (int i = 0; i < g.length; i++) consumer.accept(i);
        };
    }

    @Override
    public void addVertex(Integer integer) {
        throw new RuntimeException();
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
    public void addEdge(E edge) {
        this.g[edge.getSource()].add(edge);
    }
}
