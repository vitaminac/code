package code.adt;

public class Math {
    public static final double PI = 3.14159265358979323846;

    public static double sin(double radian) {
        if (abs(radian) > 2 * PI) radian = radian % (2 * PI);
        double sin = radian;
        double divisor = -6;
        for (int i = 3; i <= 100; i += 2) {
            sin += java.lang.Math.pow(radian, i) / divisor;
            divisor *= -1 * (i + 1) * (i + 2);
        }
        return sin;
    }

    public static double cos(double radian) {
        if (abs(radian) > 2 * PI) radian = radian % (2 * PI);
        double cos = 1;
        double divisor = -2;
        for (int i = 2; i <= 100; i += 2) {
            cos += java.lang.Math.pow(radian, i) / divisor;
            divisor *= -1 * (i + 1) * (i + 2);
        }
        return cos;
    }

    public static int abs(int n) {
        return n < 0 ? -n : n;
    }

    public static double abs(double n) {
        return n < 0 ? -n : n;
    }

    public static int min(int a, int b) {
        if (a < b) return a;
        else return b;
    }

    public static double sqrt(double n, double epsilon) {
        double estimation = 1;
        double mean;
        while (abs((mean = (estimation + (n / estimation)) / 2) - estimation) < epsilon) estimation = mean;
        return mean;
    }

    // The Euclidean Algorithm, assume a <= b
    public static int gcd(int a, int b) {
        if (a == 0) return b;
        return gcd(b % a, a);
    }
}
