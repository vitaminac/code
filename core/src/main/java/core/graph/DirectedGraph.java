package core.graph;

import java.util.function.Consumer;

import core.functional.Enumerable;
import core.linkedlist.SinglyLinkedListDoubleReference;
import core.map.SeparateChainingHashTableMap;
import core.queue.Queue;
import core.set.MutableSet;
import core.set.NavigableSet;

public interface DirectedGraph<Vertex, E extends Edge<Vertex>> {
    Enumerable<Vertex> getVertices();

    void addVertex(Vertex vertex);

    boolean isAdjacent(Vertex u, Vertex v);

    default NavigableSet<Vertex> getAdjacentVertices(Vertex vertex) {
        final var set = MutableSet.<Vertex>fromMap(SeparateChainingHashTableMap::new);
        this.getEdges(vertex).forEach(edge -> set.add(edge.getDestination()));
        return set;
    }

    Enumerable<E> getEdges(Vertex vertex);

    void addEdge(E edge);

    default Enumerable<Vertex> bfs(Vertex u) {
        return consumer -> {
            final var visited = MutableSet.<Vertex>fromMap(SeparateChainingHashTableMap::new);
            Queue<Vertex> q = Queue.fromSteque(SinglyLinkedListDoubleReference::new);
            q.enqueue(u);
            while (!q.isEmpty()) {
                var vertex = q.dequeue();
                if (!visited.contains(vertex)) {
                    consumer.accept(vertex);
                    visited.add(vertex);
                    this.getAdjacentVertices(vertex).forEach(q::enqueue);
                }
            }
        };
    }

    private void dfs(Vertex vertex, MutableSet<Vertex> visited, Consumer<? super Vertex> consumer) {
        if (!visited.contains(vertex)) {
            consumer.accept(vertex);
            visited.add(vertex);
            this.getAdjacentVertices(vertex).forEach(v -> this.dfs(v, visited, consumer));
        }
    }

    default Enumerable<Vertex> dfs(Vertex u) {
        return consumer -> this.dfs(u, MutableSet.fromMap(SeparateChainingHashTableMap::new), consumer);
    }
}
