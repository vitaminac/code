package aceptaelreto;

import java.util.Scanner;

public class P541IIII {
    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static void main(String[] args) {
        int i, v, x;
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                i = sc.nextInt();
                v = sc.nextInt();
                x = sc.nextInt();
                if (i == 0 && v == 0 && x == 0) return;
                int gcd = gcd(i, gcd(v, x));
                System.out.println(i / gcd + v / gcd + x / gcd);
            }
        }
    }
}
