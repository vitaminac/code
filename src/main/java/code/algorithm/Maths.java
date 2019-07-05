package code.algorithm;

public class Maths {
    public static final double PI = 3.14159265358979323846;

    public static double sin(double radian) {
        if (abs(radian) > 2 * PI) radian = radian % (2 * PI);
        double sin = radian;
        double divisor = -6;
        for (int i = 3; i <= 100; i += 2) {
            sin += Math.pow(radian, i) / divisor;
            divisor *= -1 * (i + 1) * (i + 2);
        }
        return sin;
    }

    public static double cos(double radian) {
        if (abs(radian) > 2 * PI) radian = radian % (2 * PI);
        double cos = 1;
        double divisor = -2;
        for (int i = 2; i <= 100; i += 2) {
            cos += Math.pow(radian, i) / divisor;
            divisor *= -1 * (i + 1) * (i + 2);
        }
        return cos;
    }

    public static double abs(double value) {
        return value > 0 ? value : -value;
    }
}
