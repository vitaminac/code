package core.graph;

import core.map.SeparateChainingHashTableMap;
import core.set.MutableSet;
import core.set.NavigableSet;

public interface UndirectedGraph<Vertex, E extends Edge<Vertex>> extends DirectedGraph<Vertex, E> {
    @Override
    default NavigableSet<Vertex> getAdjacentVertices(Vertex vertex) {
        final var set = MutableSet.<Vertex>fromMap(SeparateChainingHashTableMap::new);
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
