package code.aceptaelreto;

import java.util.Scanner;

public class P548ViajandoConElAbuelo {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int n = sc.nextInt();
                int p = sc.nextInt() + 1;
                int[] tramo = new int[n];

                long l = 0;
                long h = 0;
                for (int i = 0; i < n; i++) {
                    tramo[i] = sc.nextInt();
                    h += tramo[i];
                    l = Math.max(l, tramo[i]);
                }

                while (l < h) {
                    long mid = l + (h - l) / 2;

                    long sum = 0;
                    long require = 1;
                    for (int i = 0; i < n; i++) {
                        if (sum + tramo[i] > mid) {
                            sum = tramo[i];
                            require += 1;
                        } else {
                            sum += tramo[i];
                        }
                    }

                    if (require <= p)
                        h = mid;
                    else
                        l = mid + 1;
                }

                System.out.println(l);
            }
        }
    }
}
