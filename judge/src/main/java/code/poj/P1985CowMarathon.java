package code.poj;

import java.util.*;

public class P1985CowMarathon {
    private static int[] distance;

    private static class Edge {
        private int v;
        private int distance;

        public Edge(int v, int distance) {
            this.v = v;
            this.distance = distance;
        }
    }

    private static int bfs(int farthest, List<Edge>[] graph) {
        Queue<Integer> q = new PriorityQueue<Integer>(graph.length, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return distance[a] - distance[b];
            }
        });
        q.add(farthest);
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[farthest] = 0;
        while (!q.isEmpty()) {
            farthest = q.remove();
            for (int i = graph[farthest].size() - 1; i >= 0; i--) {
                Edge edge = graph[farthest].get(i);
                if (distance[farthest] + edge.distance < distance[edge.v]) {
                    distance[edge.v] = distance[farthest] + edge.distance;
                    q.add(edge.v);
                }
            }
        }
        return farthest;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        List<Edge>[] graph = new List[N];
        for (int i = 0; i < N; i++) graph[i] = new ArrayList<Edge>();
        // 0. Build the graph
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int d = sc.nextInt();
            String direction = sc.next();
            graph[u].add(new Edge(v, d));
            graph[v].add(new Edge(u, d));
        }
        distance = new int[N];
        // 1. Run BFS to find the farthest node u starting from an arbitrary node said s
        int u = bfs(0, graph);
        // 2. Then run BFS from u to find farthest node v
        int v = bfs(u, graph);
        // 3. Distance between node u and v is the diameter of given tree
        System.out.println(distance[v]);
    }
}