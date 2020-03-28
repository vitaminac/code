package concurso;

import java.util.Scanner;

public class SpeedRun {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int C = scanner.nextInt();
        for (int k = 0; k < C; k++) {
            int K = scanner.nextInt();
            int[][] routes = new int[K][K];
            for (int i = 0; i < K; i++)
                for (int j = 0; j < K; j++) {
                    routes[i][j] = scanner.nextInt();
                    if (routes[i][j] < 0) routes[i][j] = 1000 * 1000 + 1;
                    // calculate shortest path
                    for (int p = 0; p < i; p++) routes[i][j] = Math.min(routes[i][j], routes[i][p] + routes[p][j]);
                    // update existing path
                    for (int p = 0; p < i; p++) routes[p][j] = Math.min(routes[p][j], routes[p][i] + routes[i][j]);
                }

            int Q = scanner.nextInt();
            for (int i = 0; i < Q; i++) {
                int N = scanner.nextInt();
                int cost = 0;
                int prev;
                int next = scanner.nextInt();
                for (int j = 1; j < N; j++) {
                    prev = next;
                    next = scanner.nextInt();
                    cost += routes[prev][next];
                }
                if (cost > 1000000) {
                    System.out.println("DEAD END");
                } else {
                    int hour = (cost / 3600);
                    System.out.printf((hour < 100 ? "%02d" : "%d") + ":%02d:%02d\n", hour, (cost % 3600 / 60), (cost % 60));
                }
            }
            System.out.println("-|----");
        }
    }
}
