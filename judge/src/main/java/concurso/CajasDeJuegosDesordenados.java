package concurso;

import java.util.Scanner;

public class CajasDeJuegosDesordenados {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                int J = sc.nextInt();
                int P = sc.nextInt();
                if (J == 0 && P == 0) break;
                int[] cases = new int[J];
                for (int i = 0; i < J; i++) {
                    int A = sc.nextInt();
                    int B = sc.nextInt();
                    cases[B] = A;
                }

                // si hay algun ciclo que len(ciclo) mayor que P
                boolean[] visited = new boolean[J];
                int p = 0;
                for (int i = 0; i < J && p <= P; i++) {
                    if (!visited[i]) {
                        p = 0;
                        int current = i;
                        while (!visited[current] && p <= P) {
                            visited[current] = true;
                            p += 1;
                            current = cases[current];
                        }
                    }
                }

                System.out.println(p > P ? "NO" : "SI");
            }
        }
    }
}
