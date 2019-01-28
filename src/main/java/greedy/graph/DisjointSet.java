package greedy.graph;

public class DisjointSet {
    private final int number;
    private final int parent[];
    private final int rank[];

    public DisjointSet(int n) {
        this.number = n;
        this.parent = new int[this.number];
        for (int i = 0; i < this.number; i++) {
            this.parent[i] = -1;
        }
        this.rank = new int[this.number];
    }

    private int findSubGraph(int i) {
        int parent = i;
        if (this.parent[i] == -1) {
            return i;
        } else {
            do {
                parent = this.parent[parent];
            } while (this.parent[parent] != -1);
            // path compression
            this.parent[i] = parent;
            return parent;
        }
    }

    public void union(int u, int v) {
        int uParent = this.findSubGraph(u);
        int vParent = this.findSubGraph(v);
        if (rank[uParent] < rank[vParent]) {
            this.parent[vParent] = uParent;
            rank[uParent] += rank[vParent];
        } else {
            this.parent[uParent] = vParent;
            rank[vParent] += rank[uParent];
        }
        parent[uParent] = vParent;
    }

    public boolean isCycle(int u, int v) {
        return this.findSubGraph(u) == this.findSubGraph(v);
    }
}
