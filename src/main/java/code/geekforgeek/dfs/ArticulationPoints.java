package code.geekforgeek.dfs;

import code.adt.graph.SimpleEdge;
import code.adt.graph.SimpleUndirectedGraph;

// https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
public class ArticulationPoints {
    private void findArticulationPoint(SimpleUndirectedGraph<SimpleEdge> g, int u, boolean[] critical, boolean[] visited, int[] depth, int level) {
        // Mark the current node as visited
        visited[u] = true;
        depth[u] = level;

        g.getAdjacentVertices(u).enumerate(v -> {
            if (visited[v]) {
                depth[u] = Math.min(depth[u], depth[v] + 1);
            } else {
                this.findArticulationPoint(g, v, critical, visited, depth, level + 1);
                // if any descent node can reach a ancestor the all nodes between descent-ancestor are safe
                depth[u] = Math.min(depth[u], depth[v]);
                // (2) If u is not root and the descent cannot reach any of the ancestors
                if (depth[v] > depth[u]) critical[u] = true;
            }
        });
    }

    public boolean[] findArticulationPoint(SimpleUndirectedGraph<SimpleEdge> g) {
        boolean[] critical = new boolean[g.size()];
        this.findArticulationPoint(g, 0, critical, new boolean[g.size()], new int[g.size()], 0);
        // (1) u is root of DFS tree and has two or more children.
        critical[0] = g.getAdjacentVertices(0).size() > 1;
        return critical;
    }
}
