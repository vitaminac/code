package code.adt.graph;

public interface WeightedEdge<Vertex, SelfType extends WeightedEdge<Vertex, SelfType>>
        extends Edge<Vertex>, Comparable<SelfType> {
}
