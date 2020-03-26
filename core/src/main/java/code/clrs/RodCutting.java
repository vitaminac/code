package code.clrs;


public class RodCutting {

    private final int[] cuts;
    private final int n;
    private final double[] price;
    private final double[] revenues;

    public RodCutting(double[] price, int n) {
        this.price = price;
        this.n = n;
        this.revenues = new double[n + 1];
        this.cuts = new int[n + 1];
    }

    public double bottomUp() {
        this.revenues[0] = 0;
        for (int i = 1; i <= n; i++) {
            int cut = i;
            double max = price[i - 1];
            for (int j = 1; j < i; j++) {
                double revenue = price[j - 1] + revenues[i - j];
                if (revenue > max) {
                    max = revenue;
                    cut = j;
                }
            }
            revenues[i] = max;
            cuts[i] = cut;
        }
        return revenues[n];
    }

    public int[] getCuts() {
        return cuts;
    }

    public double topDown() {
        return topDown(this.n);
    }

    private double topDown(int n) {
        if (revenues[n] > 0) {
            return revenues[n];
        }
        if (n == 0) {
            return 0;
        } else {
            int cut = n;
            revenues[n] = price[n - 1];
            for (int i = 1; i < n; i++) {
                double revenue = price[i - 1] + topDown(n - i);
                if (revenue > revenues[n]) {
                    revenues[n] = revenue;
                    cut = i;
                }
            }
            cuts[n] = cut;
            return revenues[n];
        }
    }
}
