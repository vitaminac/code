package code.geekforgeek.greedy;

import code.adt.DisjointSet;
import code.adt.graph.SimpleUndirectedWeightedGraph;
import code.adt.graph.SimpleWeightedEdge;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class KruskalMinimumSpanningTree {
    public Set<SimpleWeightedEdge> find(SimpleUndirectedWeightedGraph g) {
        Set<SimpleWeightedEdge> candidates = new TreeSet<>();
        for (int i = 0; i < g.size(); i++) g.getEdges(i).forEach(candidates::add);
        Set<SimpleWeightedEdge> minSpanningTree = new HashSet<>();
        DisjointSet subGraphs = new DisjointSet(g.size());
        // select
        for (SimpleWeightedEdge candidate : candidates) {
            if (minSpanningTree.size() >= g.size()) {
                break;
            }
            int source = candidate.getSource();
            int destination = candidate.getDestination();
            // if is feasible
            if (!subGraphs.isCycle(source, destination)) {
                // fusion two connected subGraphs
                subGraphs.union(source, destination);
                minSpanningTree.add(candidate);
            }
        }
        return minSpanningTree;
    }
}
