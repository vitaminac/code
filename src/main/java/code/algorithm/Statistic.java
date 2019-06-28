package code.algorithm;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Statistic {
    private final static Random random = new Random();

    public static int sampleUniform(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
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