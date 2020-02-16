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

    public static long sum(long a, long b) {
        long result = a ^ b;
        long carry = (a & b) << 1;
        if (carry == 0) return result;
        else return sum(result, carry);
    }

    public static long neg(long n) {
        return sum(~n, 1);
    }

    public static long sub(long a, long b) {
        return sum(a, neg(b));
    }

    // calculate decrement by 1 exploting Two's complement without use function sub
    public static long dec(long n) {
        return ~neg(n);
    }

    public static long mul(long multiplicand, long multiplier) {
        boolean willBeNegative = false;
        long product = 0;
        if ((multiplicand & 0x80000) != 0) {
            multiplicand = neg(multiplicand);
            willBeNegative = true;
        }
        if ((multiplier & 0x80000) != 0) {
            multiplier = neg(multiplier);
            willBeNegative = !willBeNegative;
        }
        while (multiplier != 0) {
            if ((multiplier & 1) != 0) {
                product = sum(product, multiplicand);
            }
            multiplicand <<= 1;
            multiplier >>= 1;
        }
        if (willBeNegative) {
            product = neg(product);
        }
        return product;
    }

    /*
     * Booth's multiplication algorithm
     * furtuner informations can be found here: https://www.quora.com/How-does-Booths-algorithm-work
     * comment was copied from wikipedia https://en.wikipedia.org/wiki/Booth%27s_multiplication_algorithm
     */
    public static long booth_mul(int multiplicand, int multiplier) {
        // Booth's algorithm can be implemented by repeatedly adding
        // (with ordinary unsigned binary addition) one of two predetermined values
        // A and S to a product P, then performing a rightward arithmetic shift on P
        // Determine the values of A and S, and the initial value of P.

        // All of these numbers should have a length equal to(x + y + 1).
        // let x and y represent the number of bits in multiplicand and multiplier.
        // let multiplicand be 32 bit
        long x = 32;
        // let multiplier be 31 bit
        long y = 31;

        // A: Fill the most significant bits with the value of multiplicand.
        // Fill the remaining (y + 1) bits with zeros.
        long addend = ((long) multiplicand) << 32;
        // S: Fill the leftmost bits with the value of (−multiplicand) in two's complement notation.
        // Fill the remaining (y + 1) bits with zeros
        long subtrahend = neg(multiplicand) << 32;
        // P: Fill the most significant x bits with zeros.
        // To the right of this, append the value of multiplier.
        // Fill the least significant (rightmost) bit with a zero.
        long product = 0xffffffffL & (((long) multiplier) << 1);
        while (y != 0) {
            y = dec(y);
            // Determine the two least significant(rightmost) bits of P
            if ((product & 0x3) == 0x1) {
                // If they are 01, find the value of P + A. Ignore any overflow.
                product = sum(product, addend);
            } else if ((product & 0x3) == 0x2) {
                // If they are 10, find the value of P + S. Ignore any overflow.
                product = sum(product, subtrahend);
            } else {
                // If they are 00, do nothing. Use P directly in the next step.
                // If they are 11, do nothing. Use P directly in the next step.
                ; // no operation
            }
            // Arithmetically shift the value obtained in the 2nd step by a single place to the right.
            // Let P now equal this new value.
            product >>= 1;
        }
        // Drop the least significant(rightmost) bit from P.
        product >>= 1;
        // This is the product of m and r.
        return product;
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
