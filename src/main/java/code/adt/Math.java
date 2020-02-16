package code.adt;

import java.util.function.DoubleUnaryOperator;

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

    public static double derivative(DoubleUnaryOperator f, double x, double epsilon) {
        return (f.applyAsDouble(x + epsilon) - f.applyAsDouble(x - epsilon)) / (2 * epsilon);
    }

    public static int sum(int a, int b) {
        int result = a ^ b;
        int carry = (a & b) << 1;
        if (carry == 0) return result;
        else return sum(result, carry);
    }

    public static int[] extended_gcd(int a, int b) {
        int s = 0;
        int old_s = 1;
        int t = 1;
        int old_t = 0;
        int r = b;
        int old_r = a;

        while (r != 0) {
            int quotient = old_r / r;
            int tmp = old_r;
            old_r = r;
            r = tmp - quotient * r;
            tmp = old_s;
            old_s = s;
            s = tmp - quotient * s;
            tmp = old_t;
            old_t = t;
            t = tmp - quotient * t;
        }
        return new int[]{old_s, old_t};
    }
}
