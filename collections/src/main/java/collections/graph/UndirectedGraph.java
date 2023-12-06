package collections.graph;

import collections.hashtable.SeparateChainingHashTable;
import collections.map.MutableMap;
import collections.set.MutableSet;
import collections.set.NavigableSet;

public interface UndirectedGraph<Vertex, E extends Edge<Vertex>> extends DirectedGraph<Vertex, E> {
    @Override
    default NavigableSet<Vertex> getAdjacentVertices(Vertex vertex) {
        final var set = MutableSet.<Vertex>fromHashTable(SeparateChainingHashTable::new);
        this.getEdges(vertex).forEach(edge -> {
            if (vertex.equals(edge.getSource())) {
                set.add(edge.getDestination());
            } else {
                set.add(edge.getSource());
            }
        });
        return set;
    }
}
