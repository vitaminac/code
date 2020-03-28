package aceptaelreto;

import java.util.Scanner;

public class P543TensionEnLasPlayas {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            for (int t = 0; t < T; t++) {
                int n = sc.nextInt();
                int[][] sombrillas = new int[n][3];
                int count = 0;
                for (int i = 0; i < n; i++) {
                    sombrillas[i][0] = sc.nextInt();
                    sombrillas[i][1] = sc.nextInt();
                    sombrillas[i][2] = sc.nextInt();
                    for (int j = 0; j < i; j++) {
                        int distX = sombrillas[j][0] - sombrillas[i][0];
                        int distY = sombrillas[j][1] - sombrillas[i][1];
                        double distance = Math.sqrt(distX * distX + distY * distY);
                        if (distance < sombrillas[i][2] + sombrillas[j][2]) {
                            count += 1;
                        }
                    }
                }
                System.out.println(count);
            }
        }
    }
}
