package code.concurso;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GeneradorDeContrasena {
    private static long[] exp = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000};
    private static Set<Long> set = new HashSet<>(1000000);

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            for (int i = 0; i < N; i++) {
                long K = sc.nextInt();
                long X = sc.nextInt();
                long C = sc.nextInt();
                int M = sc.nextInt();
                set.clear();
                while (!set.contains(K)) {
                    set.add(K);
                    K = (K * X + C) % exp[M];
                }
                System.out.println(set.size());
            }
        }
    }
}
