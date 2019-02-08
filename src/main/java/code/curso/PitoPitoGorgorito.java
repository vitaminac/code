package code.curso;

import java.util.Scanner;

public class PitoPitoGorgorito {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            int K = scanner.nextInt();
            int answer;
            if (N < M) {
                answer = N;
            } else if (N % M == 0) {
                answer = M;
            } else {
                answer = N % M;
            }
            for (int j = 0; j < K; j++) {
                if (scanner.nextInt() == answer) {
                    System.out.println("SI");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }
}
