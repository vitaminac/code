package collections;

import java.util.Arrays;

// https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
public class DisjointSet {
    private final int[] parent;
    private final int[] rank;

    public DisjointSet(int n) {
        this.parent = new int[n];
        Arrays.fill(this.parent, -1);
        this.rank = new int[n];
    }

    private int findSubGraph(int i) {
        if (this.parent[i] == -1) return i;
        // path compression
        return this.parent[i] = this.findSubGraph(this.parent[i]);
    }

    public void union(int u, int v) {
        int uGraph = this.findSubGraph(u);
        int vGraph = this.findSubGraph(v);
        // already in the same set
        if (uGraph == vGraph) return;
        if (this.rank[uGraph] < this.rank[vGraph]) {
            this.parent[vGraph] = uGraph;
        } else if (this.rank[uGraph] > this.rank[vGraph]) {
            this.parent[uGraph] = vGraph;
        } else {
            this.parent[uGraph] = vGraph;
            this.rank[uGraph] += 1;
        }
    }

    public boolean isCycle(int u, int v) {
        return this.findSubGraph(u) == this.findSubGraph(v);
    }
}
