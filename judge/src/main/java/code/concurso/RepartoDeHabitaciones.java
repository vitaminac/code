package code.concurso;

import java.util.*;

public class RepartoDeHabitaciones {
    public static int maxflow(Set<Integer>[] graph) {
        // Ford-Fulkerson, see reference: Introduction to algorithm - pages 708~730
        int max_flow = 0;

        // init open set, close set
        int[] parent = new int[graph.length];
        Arrays.fill(parent, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(0);

        // bfs search for all possible residual paths
        while (!q.isEmpty()) {
            int u = q.remove();
            for (int v : graph[u]) {
                if (v == graph.length - 1) {
                    // found augmenting path
                    while (v != 0) {
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
                    q.add(0);
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

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            // read names
            Map<String, Integer> names = new HashMap<>();
            for (int i = 1; i <= N; i++) {
                names.put(sc.next(), i);
            }

            // adjacency list
            Set<Integer>[] graph = new Set[N * 2 + 2];
            int source = 0;
            int sink = N * 2 + 2 - 1;

            // init graph
            for (int i = source; i <= sink; i++) graph[i] = new HashSet<>();

            // flow from source to all N nodes
            for (int i = 1; i <= N; i++) graph[source].add(i);

            // flow from all N nodes to sink
            for (int i = N + 1; i <= N * 2; i++) graph[i].add(sink);

            // build graph connections from input
            for (int i = 0; i < M; i++) {
                String first = sc.next();
                String second = sc.next();
                if (first.length() % 2 == 0) {
                    graph[names.get(first)].add(names.get(second) * 2);
                } else {
                    graph[names.get(second)].add(names.get(first) * 2);
                }
            }

            System.out.println(maxflow(graph) * 2);
        }
    }
}
