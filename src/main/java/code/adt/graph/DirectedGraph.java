package code.adt.graph;

import code.adt.Enumerable;
import code.adt.DoublyLinkedList;
import code.adt.Queue;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public interface DirectedGraph<Vertex, E extends Edge<Vertex>> {
    Enumerable<Vertex> getVertices();

    void addVertex(Vertex vertex);

    boolean isAdjacent(Vertex u, Vertex v);

    default Enumerable<Vertex> getAdjacentVertices(Vertex vertex) {
        return consumer -> this.getEdges(vertex).enumerate(edge -> consumer.accept(edge.getDestination()));
    }

    Enumerable<E> getEdges(Vertex vertex);

    void addEdge(E edge);

    default Enumerable<Vertex> bfs(Vertex u) {
        return consumer -> {
            Set<Vertex> visited = new HashSet<>();
            Queue<Vertex> q = new DoublyLinkedList<>();
            q.enqueue(u);
            while (!q.isEmpty()) {
                var vertex = q.dequeue();
                if (!visited.contains(vertex)) {
                    consumer.accept(vertex);
                    visited.add(vertex);
                    this.getAdjacentVertices(vertex).enumerate(q::enqueue);
                }
            }
        };
    }

    private void dfs(Vertex vertex, Set<Vertex> visited, Consumer<? super Vertex> consumer) {
        if (!visited.contains(vertex)) {
            consumer.accept(vertex);
            visited.add(vertex);
            this.getAdjacentVertices(vertex).enumerate(v -> this.dfs(v, visited, consumer));
        }
    }

    default Enumerable<Vertex> dfs(Vertex u) {
        return consumer -> this.dfs(u, new HashSet<>(), consumer);
    }
}
