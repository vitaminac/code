package codeforces;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P1405D {
    private static final int[] DEPTH = new int[200050];
    private static final int[] HEIGHT = new int[200050];

    private static int dfs(List<Integer>[] tree, int u, int parent, int depth) {
        DEPTH[u] = depth;
        HEIGHT[u] = 0;
        int diameter = 0;
        for (int v : tree[u]) {
            if (v != parent) {
                diameter = Math.max(diameter, dfs(tree, v, u, depth + 1));
                diameter = Math.max(diameter, HEIGHT[v] + HEIGHT[u] + 1);
                HEIGHT[u] = Math.max(HEIGHT[u], HEIGHT[v] + 1);
            }
        }
        return diameter;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            for (int t = sc.nextInt(); t > 0; t--) {
                int
                        n = sc.nextInt(),
                        a = sc.nextInt(),
                        b = sc.nextInt(),
                        da = sc.nextInt(),
                        db = sc.nextInt();
                List<Integer>[] g = new List[n + 1];
                for (int i = 0; i <= n; i++) g[i] = new ArrayList<>();
                for (int i = n - 1; i > 0; i--) {
                    int u = sc.nextInt(), v = sc.nextInt();
                    g[u].add(v);
                    g[v].add(u);
                }
                DEPTH[a] = 0;
                if (dfs(g, a, -1, 0) <= da * 2 || DEPTH[b] <= da || da * 2 >= db) {
                    System.out.println("Alice");
                } else {
                    System.out.println("Bob");
                }
            }
        }
    }
}