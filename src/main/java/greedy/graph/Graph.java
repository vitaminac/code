package greedy.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
    private int n;
    private HashMap<Integer, HashSet<Edge>> edges = new HashMap<>();

    public Graph(int n) {
        this.n = n;
        for (int i = 1; i <= n; i++) {
            this.edges.put(i, new HashSet<>());
        }
    }

    public void add(Edge e) {
        this.edges.get(e.getSource()).add(e);
        this.edges.get(e.getDestination()).add(e);
    }

    public void add(int u, int v, double w) {
        this.add(new Edge(u, v, w));
    }

    public int getNumberOfVertices() {
        return this.n;
    }

    public Iterable<Edge> getEdges(int v) {
        return this.edges.get(v);
    }

    public Set<Edge> getAllEdges() {
        HashSet<Edge> edges = new HashSet<>();
        for (HashSet<Edge> edgesV : this.edges.values()) {
            edges.addAll(edgesV);
        }
        return edges;
    }

    // Kruskal Algorithm
    public Set<Edge> getMinSpanningTree() {
        TreeSet<Edge> candidates = new TreeSet<>(this.getAllEdges());
        Set<Edge> minSpanningTree = new HashSet<>();
        int subGraphs[] = new int[this.getNumberOfVertices() + 1];
        while (minSpanningTree.size() < this.getNumberOfVertices() - 1 && !candidates.isEmpty()) {
            // select
            Edge candidate = candidates.pollFirst();
            int source = candidate.getSource();
            int destination = candidate.getDestination();
            // if is feasible
            if (subGraphs[source] != subGraphs[destination]) {
                // fusion two connected subGraphs
                if (subGraphs[source] == 0) {
                    subGraphs[source] = subGraphs[destination];
                } else if (subGraphs[destination] == 0) {
                    subGraphs[destination] = subGraphs[source];
                } else {
                    int flag = subGraphs[destination];
                    for (int i = 1; i <= this.getNumberOfVertices(); i++) {
                        if (subGraphs[i] == flag) {
                            subGraphs[i] = subGraphs[source];
                        }
                    }
                }
                minSpanningTree.add(candidate);
            } else {
                if (subGraphs[source] == 0) {
                    subGraphs[source] = source;
                    subGraphs[destination] = source;
                    minSpanningTree.add(candidate);
                }
            }
        }
        return minSpanningTree;
    }
}
