package code.algorithm;

import java.util.Random;

public class Statistic {
    private final static Random rand = new Random();

    public double nextExp(double lambda) {
        return Math.log(1 - rand.nextDouble()) / (-lambda);
    }

    // slow
    public static double binomialCDFRecursive(int N, int k, double p) {
        if (N == 0 && k == 0)
            return 1.0;
        if (N < 0 || k < 0)
            return 0.0;
        return (1.0 - p) * binomialCDFRecursive(N - 1, k, p) + p * binomialCDFRecursive(N - 1, k - 1, p);
    }

    // memoization
    public static double binomialCDFIterative(int N, int k, double p) {
        double[][] b = new double[N + 1][k + 1];

        // base cases
        for (int i = 0; i <= N; i++)
            b[i][0] = Math.pow(1.0 - p, i);
        b[0][0] = 1.0;

        // recursive formula
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= k; j++) {
                b[i][j] = p * b[i - 1][j - 1] + (1.0 - p) * b[i - 1][j];
            }
        }
        return b[N][k];
    }
}