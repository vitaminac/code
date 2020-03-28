package concurso;

import java.util.Scanner;

public class AyudameAtica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int m = scanner.nextInt();
            int hour = scanner.nextInt();
            for (int j = 0; j < m; j++) {
                hour -= Math.max(50 - scanner.nextInt(), 0);
            }
            if (hour < 0) {
                System.out.println("NOS VEMOS EN JUNIO...");
            } else {
                System.out.println("APRUEBA TODO");
            }
        }
    }
}
