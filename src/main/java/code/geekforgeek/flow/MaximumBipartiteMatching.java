package code.geekforgeek.flow;

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

        // flow from N nodes to sink
        for (int i = M + 1; i <= M + N; i++) graph[i].add(sink);

        // build graph
        for (int i = 0; i < M; i++) for (int j = 0; j < N; j++) if (matches[i][j]) graph[i + 1].add(j + M + 1);

        int max_flow = 0;

        int[] parent = new int[graph.length];
        Arrays.fill(parent, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        // bfs search for all possible residual path
        while (!q.isEmpty()) {
            int u = q.remove();
            for (int v : graph[u]) {
                if (v == sink) {
                    // increase augmenting path
                    while (v != source) {
                        graph[u].remove(v);
                        graph[v].add(u);
                        v = u;
                        u = parent[u];
                    }
                    max_flow += 1;
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
