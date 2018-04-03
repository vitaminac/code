package greedy.graph;

public class DisjointSet {
    private final int number;
    private final int parent[];

    public DisjointSet(int n) {
        this.number = n;
        this.parent = new int[this.number];
        for (int i = 0; i < this.number; i++) {
            this.parent[i] = -1;
        }
    }

    private int findParent(int i) {
        while (parent[i] != -1) {
            i = parent[i];
        }
        return i;
    }

    public void union(int u, int v) {
        int uParent = this.findParent(u);
        int vParent = this.findParent(v);
        parent[uParent] = vParent;
    }

    public boolean isCycle(int u, int v) {
        return this.findParent(u) == this.findParent(v);
    }
}
