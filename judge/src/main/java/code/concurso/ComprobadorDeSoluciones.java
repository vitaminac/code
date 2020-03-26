package code.concurso;

import java.util.Scanner;

public class ComprobadorDeSoluciones {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            for (int i = 0; i < T; i++) {
                if (sc.next().equals(sc.next())) {
                    System.out.println("Todo correcto");
                } else {
                    System.out.println("REVISAR!");
                }
            }
        }
    }
}
