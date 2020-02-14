package code.codeforces;

import java.util.Scanner;

public class P997 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            int k = sc.nextInt();
            for (int i = 0; i < k; i++) {
                if (N % 10 == 0) {
                    N = N / 10;
                } else {
                    N -= 1;
                }
            }
            System.out.println(N);
        }
    }
}
