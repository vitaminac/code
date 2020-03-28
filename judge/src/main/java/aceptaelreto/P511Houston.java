package aceptaelreto;

import java.util.Scanner;

public class P511Houston {
    private static int[] groupA = new int[30005];
    private static int[] valueA = new int[30005];
    private static int[] groupB = new int[30005];
    private static int[] valueB = new int[30005];

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int M = sc.nextInt();
                int N = sc.nextInt();
                for (int i = 0; i < M; i++) {
                    groupA[i] = sc.nextInt();
                    valueA[i] = sc.nextInt();
                }
                for (int i = 0; i < N; i++) {
                    groupB[i] = sc.nextInt();
                    valueB[i] = sc.nextInt();
                }
                long sum = 0;
                int i = 0, j = 0;
                while (i < M || j < N) {
                    long step = Math.min(groupA[i], groupB[j]);
                    sum += step * valueA[i] * valueB[j];
                    groupA[i] -= step;
                    groupB[j] -= step;
                    if (groupA[i] == 0) ++i;
                    if (groupB[j] == 0) ++j;
                }
                System.out.println(sum);
            }
        }
    }
}
