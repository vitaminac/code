package concurso;

import java.util.*;
import java.util.stream.IntStream;

public class CarreraEnPekinExpress {
    private static class Node implements Comparable<Node> {
        private final int vertex;
        private final int distance;

        public Node(final int vertex, final int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            int C = sc.nextInt();
            List<Node>[] g = new List[N];
            for (int i = 0; i < N; i++) g[i] = new ArrayList<>();
            for (int i = 0; i < C; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int D = sc.nextInt();
                g[u].add(new Node(v, D));
                g[v].add(new Node(u, D));
            }

            int source = sc.nextInt();

            // dijkstra
            int[] distances = new int[N];
            boolean[] visited = new boolean[N];
            final PriorityQueue<Node> Q = new PriorityQueue<>();
            Arrays.fill(distances, 0, N, Integer.MAX_VALUE);
            distances[source] = 0;
            Q.add(new Node(source, 0));
            for (int i = 0; i < N; i++) {
                Node from;
                do {
                    from = Q.remove();
                } while (visited[from.vertex]);
                visited[from.vertex] = true;
                for (final Node to : g[from.vertex]) {
                    final int alt = distances[from.vertex] + to.distance;
                    if (alt < distances[to.vertex]) {
                        distances[to.vertex] = alt;
                        Q.add(new Node(to.vertex, alt));
                    }
                }
            }

            int E = sc.nextInt();
            int[] cost = new int[E];
            for (int i = 0; i < E; i++) {
                cost[i] = distances[sc.nextInt()] + sc.nextInt();
            }

            int[] sortedIndices = IntStream.range(0, E)
                    .boxed()
                    .sorted(Comparator.<Integer>comparingInt(i -> cost[i]).thenComparingInt(i -> i))
                    .mapToInt(i -> i)
                    .toArray();

            StringBuilder sb = new StringBuilder();
            sb.append(sortedIndices[0]);
            for (int i = 1; i < E; i++) {
                if (cost[sortedIndices[i]] == cost[sortedIndices[i - 1]]) {
                    sb.append(" = ");
                } else {
                    sb.append(" -> ");
                }
                sb.append(sortedIndices[i]);
            }
            System.out.println(sb.toString());
        }
    }
}
