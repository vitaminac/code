package aceptaelreto;

import java.util.Scanner;

public class P545AhorrandoFuerzas {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int l = sc.nextInt(), n = sc.nextInt(), min = Integer.MAX_VALUE;
                for (int i = 0; i < n; i++) {
                    int t = sc.nextInt();
                    int v = sc.nextInt();
                    if (t >= 0) {
                        min = Math.min(min, t + l / v);
                    }
                }
                System.out.println(min);
            }
        }
    }
}
