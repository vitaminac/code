package concurso;

import java.util.Scanner;

public class ComprasExtremas {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            String[] names = new String[T];
            int[] prices = new int[T];
            int best = 0;
            for (int i = 0; i < T; i++) {
                int N = sc.nextInt();
                names[i] = sc.next();
                for (int j = 0; j < N; j++) {
                    String product = sc.next();
                    prices[i] += sc.nextInt();
                }
                if (prices[i] > prices[best]) best = i;
            }
            System.out.println(names[best] + " " + prices[best]);
        }
    }
}
