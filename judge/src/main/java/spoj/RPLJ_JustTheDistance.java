package spoj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// https://www.spoj.com/problems/RPLJ/
public class RPLJ_JustTheDistance {
    private static final int MAX_N = 1005;
    private static final char[][] map = new char[MAX_N][];
    private static final List<Integer> fromA = new ArrayList<>(MAX_N);
    private static final List<Integer> fromB = new ArrayList<>(MAX_N);

    private static void dfs(int i, int j, int N, List<Integer> from) {
        if (i < 0 || i >= N || j < 0 || j >= N || map[i][j] != '*') return;
        map[i][j] = 'x';
        from.add(i * N + j);
        dfs(i - 1, j, N, from);
        dfs(i + 1, j, N, from);
        dfs(i, j - 1, N, from);
        dfs(i, j + 1, N, from);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            for (int t = 1; t <= T; t++) {
                // read input
                int N = sc.nextInt();
                sc.nextLine();
                for (int i = 0; i < N; i++) {
                    map[i] = sc.nextLine().toCharArray();
                }
                fromA.clear();
                fromB.clear();
                // process input
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (map[i][j] == '*') {
                            dfs(i, j, N, fromA.size() == 0 ? fromA : fromB);
                        }
                    }
                }
                int min_diagonal_steps = Integer.MAX_VALUE, min_normal_steps = Integer.MAX_VALUE;
                for (int a : fromA) {
                    for (int b : fromB) {
                        int aX = a / N;
                        int aY = a % N;
                        int bX = b / N;
                        int bY = b % N;
                        min_diagonal_steps = Math.min(min_diagonal_steps, Math.max(Math.abs(aX - bX), Math.abs(aY - bY)));
                        min_normal_steps = Math.min(min_normal_steps, Math.abs(aX - bX) + Math.abs(aY - bY));
                    }
                }
                System.out.println("Scenario #" + t + ": " + (min_diagonal_steps < min_normal_steps ? "0" : "1"));
            }
        }
    }
}
