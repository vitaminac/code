package code.curso;

import java.util.Scanner;

public class HolaDonPepito {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            String name = scanner.next();
            String G = scanner.next();
            if (G.equals("F")) {
                System.out.println("Hola Donya " + name);
            } else {
                System.out.println("Hola Don " + name);
            }

            name = scanner.next();
            G = scanner.next();
            if (G.equals("F")) {
                System.out.println("Hola Donya " + name);
            } else {
                System.out.println("Hola Don " + name);
            }
        }
    }
}
