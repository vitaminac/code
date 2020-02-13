package code.concurso;

import java.util.Scanner;

public class LlenandoElAutocar {
    private static long[] sum = new long[100000];

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int N = sc.nextInt();
                sum[0] = sc.nextInt();
                for (int i = 1; i < N; i++) {
                    sum[i] = sum[i - 1] + sc.nextInt();
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < N; i++) {
                    int k = i;
                    if (sum[N - 1] % sum[i] != 0) continue;
                    for (int j = k + 1; j < N; j++) {
                        if (sum[j] - sum[k] == sum[i]) {
                            k = j;
                        } else if (sum[j] - sum[k] > sum[i]) {
                            break;
                        }
                    }
                    if (k + 1 == N) {
                        sb.append(sum[i]);
                        sb.append(' ');
                    }
                }
                sb.setLength(sb.length() - 1);
                System.out.println(sb.toString());
            }
        }
    }
}
