package concurso;

import java.util.Scanner;

public class CeroVirus {
    private static int find(int[] uf, int u) {
        if (uf[u] == u) return uf[u];
        else return uf[u] = find(uf, uf[u]);
    }

    private static void union(int[] uf, int u, int v, boolean[] infected) {
        int p1 = find(uf, u);
        int p2 = find(uf, v);
        uf[p1] = p2;
        infected[p2] |= infected[p1];
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int[] uf = new int[N + 1];
            boolean[] infected = new boolean[N + 1];
            for (int i = 0; i <= N; i++) uf[i] = i;
            while (sc.hasNext()) {
                String token = sc.next();
                if (token.equals("CONTACTO")) {
                    union(uf, sc.nextInt(), sc.nextInt(), infected);
                } else if (token.equals("?")) {
                    System.out.println(infected[find(uf, sc.nextInt())] ? "POSIBLE" : "NO");
                } else if (token.equals("POSITIVO")) {
                    infected[find(uf, sc.nextInt())] = true;
                }
            }
        }
    }
}
