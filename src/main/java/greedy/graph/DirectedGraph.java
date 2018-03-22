package greedy.graph;

import java.util.HashSet;

public class DirectedGraph {
    private final HashSet<Integer> adj[];
    private int n;

    public DirectedGraph(int n) {
        this.n = n;
        this.adj = new HashSet[n];
        for (int i = 0; i < n; i++) {
            this.adj[i] = new HashSet<Integer>();
        }
    }

    // Function that returns reverse (or transpose) of this graph
    public DirectedGraph getTranspose() {
        DirectedGraph newGraph = new DirectedGraph(n);
        for (int i = 0; i < n; i++) {
            // Recur for all the vertices adjacent to this vertex
            for (int j : this.getAdjacents(i)) {
                newGraph.adj[j].add(j);
            }
        }
        return newGraph;
    }

    public int getNumVertices() {
        return n;
    }

    public void addEdge(int u, int v) {
        this.adj[u].add(v);
    }

    public HashSet<Integer> getAdjacents(int v) {
        return this.adj[v];
    }
}
