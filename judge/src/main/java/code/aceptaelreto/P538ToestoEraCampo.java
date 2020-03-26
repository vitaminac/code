package code.aceptaelreto;

import java.util.Scanner;

public class P538ToestoEraCampo {
    public static void main(String[] args) {
        int n, m;
        try (Scanner sc = new Scanner(System.in)) {
            do {
                n = sc.nextInt();
                m = sc.nextInt();
                if (n == 0 && m == 0) return;
                System.out.println(n < m ? "SENIL" : "CUERDO");
            } while (true);
        }
    }
}
