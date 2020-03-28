package topcoder;

public class ShootingGame {
    public double findProbability(int p) {
        if (p >= 500000) return -1;
        double q = 1000000 - p;
        return ((double) p) / q;
    }
}
