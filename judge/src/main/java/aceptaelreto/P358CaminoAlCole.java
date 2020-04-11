package aceptaelreto;

import java.util.*;

public class P358CaminoAlCole {
    private static final int MAX_N = 10000 + 1;
    private static final List<Edge>[] g = new List[MAX_N];
    private static final int[] distances = new int[MAX_N];
    private static final boolean[] visited = new boolean[MAX_N];

    private static class Edge implements Comparable<Edge> {
        private final int vertex;
        private final int distance;

        public Edge(final int vertex, final int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(final Edge edge) {
            return this.distance - edge.distance;
        }
    }

    public static void main(final String[] args) {
        try (final Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                final int N = sc.nextInt();
                for (int i = 1; i <= N; i++) g[i] = new ArrayList<>();
                final int C = sc.nextInt();
                for (int i = 0; i < C; i++) {
                    final int from = sc.nextInt();
                    final int to = sc.nextInt();
                    final int weight = sc.nextInt();
                    g[from].add(new Edge(to, weight));
                    g[to].add(new Edge(from, weight));
                }

                final List<Integer> topologicalOrder = new ArrayList<>();

                // dijkstra
                // create vertex priority queue Q
                final PriorityQueue<Edge> Q = new PriorityQueue<>();
                // for each vertex v in Graph:
                // dist[v] <= inf
                Arrays.fill(distances, 1, N + 1, Integer.MAX_VALUE);
                // visited[v] <= false
                Arrays.fill(visited, 1, N + 1, false);
                // dist[source] <= 0
                distances[N] = 0;
                // add source to Q
                Q.add(new Edge(N, 0));
                while (!Q.isEmpty()) {
                    // u <= Q.extract_min()
                    final Edge to = Q.remove();
                    final int u = to.vertex;
                    if (!visited[u]) {
                        if (u == 1) break;
                        visited[u] = true;
                        topologicalOrder.add(u);
                        // for each neighbor v of u:
                        for (final Edge edge : g[u]) {
                            final int v = edge.vertex;
                            final int alt = distances[u] + edge.distance;
                            if (alt < distances[v]) {
                                distances[v] = alt;
                                // Q.add_with_priority(v, dist[v])
                                Q.add(new Edge(v, alt));
                            }
                        }
                    }
                }

                // compute count
                final Map<Integer, Integer> count = new HashMap<>();
                count.put(1, 0); // default value if there is no path to origin
                count.put(N, 1);
                for (final int u : topologicalOrder) {
                    for (final Edge to : g[u]) {
                        final int v = to.vertex;
                        if (distances[u] + to.distance == distances[v]) {
                            count.put(v, (count.containsKey(v) ? count.get(v) : 0) + count.get(u));
                        }
                    }
                }

                System.out.println(count.get(1));
            }
        }
    }
}
