package code.geekforgeek.greedy;

import code.adt.graph.SimpleUndirectedWeightedGraph;
import code.adt.graph.SimpleWeightedEdge;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

// https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/
public class PrimMinimumSpanningTree {
    public Set<SimpleWeightedEdge> find(SimpleUndirectedWeightedGraph g) {
        // Keeps track of vertices already included in MST
        boolean[] visited = new boolean[g.size()];
        visited[0] = true;
        Set<SimpleWeightedEdge> minSpanningTree = new HashSet<>();
        Queue<SimpleWeightedEdge> candidate = new PriorityQueue<>();
        g.getEdges(0).enumerate(candidate::add);
        while (minSpanningTree.size() < g.size() - 1) {
            final SimpleWeightedEdge edge = candidate.remove();
            if (!visited[edge.getSource()]) {
                visited[edge.getSource()] = false;
                g.getEdges(edge.getSource()).enumerate(candidate::add);
                minSpanningTree.add(edge);
            } else if (!visited[edge.getDestination()]) {
                visited[edge.getDestination()] = false;
                g.getEdges(edge.getDestination()).enumerate(candidate::add);
                minSpanningTree.add(edge);
            }
        }
        return minSpanningTree;
    }
}
