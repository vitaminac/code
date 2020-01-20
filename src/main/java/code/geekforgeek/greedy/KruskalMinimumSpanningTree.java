package code.geekforgeek.greedy;

import code.adt.graph.SimpleUndirectedGraph;
import code.adt.graph.SimpleWeightedEdge;
import code.adt.DisjointSet;

import java.util.*;

public class KruskalMinimumSpanningTree {
    public Set<SimpleWeightedEdge> find(SimpleUndirectedGraph<SimpleWeightedEdge> g) {
        Set<SimpleWeightedEdge> candidates = new TreeSet<>();
        for (int i = 0; i < g.size(); i++) g.getEdges(i).enumerate(candidates::add);
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
