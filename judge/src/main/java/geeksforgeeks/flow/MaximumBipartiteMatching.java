package geeksforgeeks.flow;

import java.util.*;

// https://www.geeksforgeeks.org/maximum-bipartite-matching/
public class MaximumBipartiteMatching {
    public int bmp(boolean[][] matches) {
        int M = matches.length;
        int N = matches[0].length;

        // adjacency list
        Set<Integer>[] graph = new Set[M + N + 2];

        int source = 0;
        int sink = M + N + 2 - 1;

        // init graph
        for (int i = 0; i < graph.length; i++) graph[i] = new HashSet<>();

        // flow from source to all M nodes
        for (int i = 1; i <= M; i++) graph[source].add(i);

        // flow from all N nodes to sink
        for (int i = M + 1; i <= M + N; i++) graph[i].add(sink);

        // build graph connections from input
        for (int i = 0; i < M; i++) for (int j = 0; j < N; j++) if (matches[i][j]) graph[i + 1].add(j + M + 1);

        // Ford-Fulkerson, see reference: Introduction to algorithm - pages 708~730
        int max_flow = 0;

        // init open set, close set
        int[] parent = new int[graph.length];
        Arrays.fill(parent, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        // bfs search for all possible residual paths
        while (!q.isEmpty()) {
            int u = q.remove();
            for (int v : graph[u]) {
                if (v == sink) {
                    // found augmenting path
                    while (v != source) {
                        // remove the path and add the reverse direction
                        graph[u].remove(v);
                        graph[v].add(u);
                        v = u;
                        u = parent[u];
                    }
                    // increase value
                    max_flow += 1;
                    // reset open-set, close-set
                    Arrays.fill(parent, -1);
                    q.clear();
                    q.add(source);
                    break;
                } else if (/* test if visited */ parent[v] == -1) {
                    // save path
                    parent[v] = u;
                    q.add(v);
                }
            }
        }

        return max_flow;
    }
}
