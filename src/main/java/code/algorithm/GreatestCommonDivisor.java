package code.algorithm;

/**
 * The Euclidean Algorithm
 **/
public class GreatestCommonDivisor {
    public static int gcd(int a, int b) {
        if (a == 0) {
            return b;
        } else if (b == 0) {
            return a;
        } else {
            if (a > b) {
                return gcd(a % b, b);
            } else {
                return gcd(a, b % a);
            }
        }
    }
}
