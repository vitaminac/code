package code.aceptaelreto;

import java.util.Scanner;

public class P539FelizDecadaNueva {
    public static void main(String[] args) {
        int n, m;
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                n = sc.nextInt() % 10;
                m = sc.nextInt() % 10;
                System.out.println(n != (m + 1) % 10 ? "TOCA ESPERAR" : "FELIZ DECADA NUEVA");
            }
        }
    }
}
