package greedy.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
    private int n;
    private HashMap<Integer, TreeSet<Edge>> edges = new HashMap<>();

    public Graph(int n) {
        this.n = n;
        for (int i = 0; i < n; i++) {
            this.edges.put(i, new TreeSet<>());
        }
    }

    public void add(int u, int v, double w) {
        final Edge edge = new Edge(u, v, w);
        this.edges.get(u).add(edge);
        this.edges.get(v).add(edge);
    }

    public int getNumberOfVertices() {
        return this.n;
    }

    public Set<Edge> getEdges(int v) {
        return this.edges.get(v);
    }

    // Kruskal Algorithm
    public Set<Edge> getMinSpanningTreeKruskal() {
        List<Edge> candidates = new ArrayList<>();
        for (TreeSet<Edge> edges : this.edges.values()) {
            candidates.addAll(edges);
        }
        Collections.sort(candidates);
        Set<Edge> minSpanningTree = new HashSet<>();
        DisjointSet subGraphs = new DisjointSet(this.n);
        // select
        for (Edge candidate : candidates) {
            if (minSpanningTree.size() >= this.getNumberOfVertices()) {
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

    // Prim Algorithm
    public Set<Edge> getMinSpanningTreePrim() {
        Set<Integer> candidates = new HashSet<>(this.edges.keySet());
        candidates.remove(0);
        Set<Edge> minSpanningTree = new HashSet<>();
        PriorityQueue<Edge> paths = new PriorityQueue<>(this.getEdges(0));
        while (minSpanningTree.size() < this.getNumberOfVertices() - 1) {
            final Edge edge = paths.remove();
            if (candidates.contains(edge.getSource())) {
                candidates.remove(edge.getSource());
                paths.addAll(this.getEdges(edge.getSource()));
                minSpanningTree.add(edge);
            } else if (candidates.contains(edge.getDestination())) {
                candidates.remove(edge.getDestination());
                paths.addAll(this.getEdges(edge.getDestination()));
                minSpanningTree.add(edge);
            }
        }
        return minSpanningTree;
    }
}
