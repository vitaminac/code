package code.concurso;

import java.util.Scanner;

public class Adrenalina {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            int T = scanner.nextInt();
            int K = scanner.nextInt();
            boolean[] bitMap = new boolean[K];
            for (int j = 0; j < T; j++) {
                bitMap[scanner.nextInt()] = true;
            }
            int ADRENALINA = 0;
            int ARRIESGA = 0;
            int SALVADO = 0;
            for (int j = 0; j < K; j++) {
                if (bitMap[j]) {
                    if (bitMap[(j + 1) % K] && bitMap[(j - 1 + K) % K]) {
                        ++ADRENALINA;
                    } else if (bitMap[(j + 1) % K] || bitMap[(j - 1 + K) % K]) {
                        ++ARRIESGA;
                    } else {
                        ++SALVADO;
                    }
                }
            }
            System.out.println("ADRENALINA " + ADRENALINA);
            System.out.println("ARRIESGA " + ARRIESGA);
            System.out.println("SALVADO " + SALVADO);
        }
    }
}
