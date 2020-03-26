package code.algorithm;

import java.util.Random;

public class Sattolo {
    private static final long SEED = 30L;

    public static void cycle(Object[] a) {
        int n = a.length;
        Random generator = new Random(SEED);
        ;
        for (int i = n; i > 1; i--) {
            // choose index uniformly in [0, i-1)
            int r = generator.nextInt(i - 1);
            Object swap = a[r];
            a[r] = a[i - 1];
            a[i - 1] = swap;
        }
    }
}
