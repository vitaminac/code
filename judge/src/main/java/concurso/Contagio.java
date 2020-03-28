package concurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contagio {
    private static int dfs(int p, int[] people, List<Integer>[] g) {
        if (people[p] == 0) {
            people[p] = 1;
            int count = 1;
            if (g[p] != null) for (int other : g[p]) count += dfs(other, people, g);
            return count;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            int[] people = new int[N + 5];
            List<Integer>[] g = new List[N + 5];
            int K = sc.nextInt();
            for (int i = 0; i < K; i++) people[sc.nextInt()] = -1;
            int I = sc.nextInt();
            int M = sc.nextInt();
            for (int i = 0; i < M; i++) {
                int A = sc.nextInt();
                int B = sc.nextInt();
                if (g[A] == null) {
                    g[A] = new ArrayList<>();
                }
                if (g[B] == null) {
                    g[B] = new ArrayList<>();
                }
                g[A].add(B);
                g[B].add(A);
            }
            System.out.println(dfs(I, people, g));
        }
    }
}
