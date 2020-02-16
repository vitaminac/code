package code.geekforgeek.dfs;

import code.adt.graph.SimpleUndirectedGraph;

// https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
public class ArticulationPoints {
    private int time;
    private int[] timestamp;
    private boolean[] critical;
    private SimpleUndirectedGraph g;

    private void findArticulationPoint(int u, int parent) {
        // Mark the current node as visited, if timestamp[u] == 0, then it is not visited
        // record the discovery timestamp
        int currentTimeStamp = timestamp[u] = ++this.time;

        // Go through all vertices adjacent to this
        g.getAdjacentVertices(u).forEach(v -> {
            // skip child-parent connection to prevent the infinite loop
            if (v == parent) return;

            // If v is not visited yet, then dfs for it
            if (timestamp[v] == 0) this.findArticulationPoint(v, u);

            // Check if the subtree rooted with v has a
            // connection to one of the ancestors of u
            timestamp[u] = Math.min(timestamp[u], timestamp[v]);

            // If one of the child doesn't have any descent to u's ancestor
            // then u is articulation point
            if (currentTimeStamp < timestamp[v]) critical[u] = true;
        });
    }

    public boolean[] findArticulationPoint(SimpleUndirectedGraph g) {
        this.g = g;
        this.time = 0;
        this.critical = new boolean[g.size()];
        this.timestamp = new int[g.size()];
        this.findArticulationPoint(0, -1);
        // (1) u is root of DFS tree and has two or more children.
        critical[0] = g.getAdjacentVertices(0).size() > 1;
        return critical;
    }
}
