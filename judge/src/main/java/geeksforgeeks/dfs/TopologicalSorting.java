package geeksforgeeks.dfs;

import collections.graph.SimpleDirectedGraph;

import java.util.Collections;
import java.util.Stack;

public class TopologicalSorting {
    private void topologicalVisit(int u, SimpleDirectedGraph g, boolean[] visited, Stack<Integer> sorting) {
        visited[u] = true;
        g.getAdjacentVertices(u).forEach(v -> {
            if (!visited[v]) {
                this.topologicalVisit(v, g, visited, sorting);
            }
        });
        sorting.push(u);
    }

    public Stack<Integer> getTopologicalSorting(SimpleDirectedGraph g) {
        Stack<Integer> sorting = new Stack<>();
        boolean[] visited = new boolean[g.size()];
        for (int j = 0; j < g.size(); j++) {
            if (!visited[j]) {
                this.topologicalVisit(j, g, visited, sorting);
            }
        }
        Collections.reverse(sorting);
        return sorting;
    }
}
