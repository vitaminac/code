package code.curso;

import java.util.Scanner;

public class VictoriaMagistral {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int dividend = scanner.nextInt();
            int divisor = scanner.nextInt();
            System.out.println(dividend / divisor);
        }
    }
}
