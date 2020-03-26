package code.spoj;

import java.util.*;

public class PT07ZLongestPathInATree {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // 0. Build the graph
            int N = sc.nextInt();
            List<Integer>[] graph = new List[N];
            for (int i = 0; i < N; i++) graph[i] = new ArrayList<>();
            for (int i = 1; i < N; i++) {
                int u = sc.nextInt() - 1;
                int v = sc.nextInt() - 1;
                graph[u].add(v);
                graph[v].add(u);
            }
            // 1. Run BFS to find the farthest node u starting from an arbitrary node said s
            int farthest = 0;
            Queue<Integer> q = new LinkedList<>();
            q.add(0);
            boolean[] visited = new boolean[N];
            visited[0] = true;
            while (!q.isEmpty()) {
                for (int i = q.size(); i > 0; i--) {
                    farthest = q.remove();
                    for (int v : graph[farthest]) {
                        if (!visited[v]) {
                            visited[v] = true;
                            q.add(v);
                        }
                    }
                }
            }
            // 2. Then run BFS from u to find farthest node v
            q.add(farthest);
            Arrays.fill(visited, false);
            visited[farthest] = true;
            int distance = 0;
            while (!q.isEmpty()) {
                distance += 1;
                for (int i = q.size(); i > 0; i--) {
                    farthest = q.remove();
                    for (int v : graph[farthest]) {
                        if (!visited[v]) {
                            visited[v] = true;
                            q.add(v);
                        }
                    }
                }
            }
            // 3. Distance between node u and v is the diameter of given tree
            System.out.println(distance - 1);
        }
    }
}