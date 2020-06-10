package core;

import java.math.BigInteger;
import java.util.function.DoubleUnaryOperator;

public class Math {
    public static int abs(int n) {
        return n < 0 ? -n : n;
    }

    public static double abs(double n) {
        return n < 0 ? -n : n;
    }

    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    public static long min(long a, long b) {
        return a < b ? a : b;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

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

    public static long[] divide(long dividend, long divisor) {
        long remainder;
        // determinate the sign of dividend and divisor, and future quotient
        boolean willBeNegative = false;
        boolean remainder_sign = false;
        if (dividend < 0) {
            dividend = neg(dividend);
            willBeNegative = true;
            remainder_sign = true;
        }
        if (divisor < 0) {
            divisor = neg(divisor);
            willBeNegative ^= true;
        }

        // invert order and add a highest bit,
        // for example dividend=101011 invert=1110101, initial invert value 2 will add a highest bit
        // it's for knowing where the number finish
        // if not dividend=1010, will invert to 0101, the origin zero will be ignore
        // we cant determinate the origin number length
        long invert = 2;
        while (dividend != 0) {
            invert |= dividend & 0x1;
            invert = invert << 1;
            dividend = dividend >> 1;
        }

        long quotient = 0;
        remainder = 0;
        // invert = 2 set highest bit as a delimiter
        while ((invert & ~0x1L) != 0)// until delimiter, the highest bit
        {
            remainder = remainder << 1;
            remainder |= invert & 0x1;
            invert = invert >> 1;
            quotient = quotient << 1;

            if (remainder > divisor) {
                quotient |= 0x1;
                remainder = sub(remainder, divisor);
            }
        }

        // apply sign to the quotient
        if (willBeNegative) {
            quotient = neg(quotient);
        }

        // apply sign to the remainder
        if (remainder_sign) {
            remainder = neg(remainder);
        }

        return new long[]{quotient, remainder};
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

    public static long[] identity(int n) {
        long[] mat = new long[n * n];
        for (int i = 0; i < n; i++) mat[i * n + i] = 1;
        return mat;
    }

    public static long[] mat_mul(long[] mat1, long[] mat2, int l, int m, int n) {
        long[] result = new long[l * n];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    result[i * n + j] += mat1[i * m + k] * mat2[k * n + j];
                }
            }
        }
        return result;
    }

    public static long permutation(long n, int k) {
        return binpow(n, k);
    }

    /*
     * Exponentiation by squaring
     *
     * https://cp-algorithms.com/algebra/binary-exp.html
     */
    public static long binpow(long base, int exp) {
        if (exp == 0) return 1;
        else if ((exp & 0x1) == 0) return binpow(base * base, exp >> 1);
        else return binpow(base, exp - 1) * base;
    }

    /*
     * The operation of modular exponentiation calculates the remainder
     * when an integer b (the base) raised to the eth power (the exponent),
     * b^e, is divided by a positive integer m
     * the modular exponentiation c is c = b^e mod m
     *
     * https://cp-algorithms.com/algebra/binary-exp.html
     */
    public static long bin_pow_mod(long base, long exp, long modulo) {
        long result = 1;
        base = base % modulo;
        // exponent e be converted to binary notation
        // exp = ∑_{i=0}^{n-1} exp_i*2^i; exp_i in {0, 1}
        // base^e = base^{∑_{i=0}^{n-1} exp_i*2^i} = ∏_{i=0}^{n-1} base^2^i)^exp_i
        // result = ∏_{i=0}^{n-1} (b^2^i)^exp_i (mod m)
        while (exp > 0) {
            // if exp_i = 0; pass; because base^0 = 1
            // if exp_i = 1; (b^2^i * (∏_{j=0}^{i-1} (b^2^j)^exp_j mod m)) mod m
            if ((exp & 0x1) != 0) {
                // ab mod m ≡ (a * (b mod m)) mod m
                result = (result * base) % modulo;
            }
            // a ≡ b mod m => a^2 ≡ b^2 mod m
            // b^2^(i+1) ≡ (b^2^(i) * b^2^(i)) mod m
            base = (base * base) % modulo;
            exp >>= 1;
        }
        return result;
    }

    public static long[] bin_pow_mat(long[] base, int n, long exp) {
        if (exp == 0) return identity(n);
        else if ((exp & 0x1) == 0) return bin_pow_mat(mat_mul(base, base, n, n, n), n, exp >> 1);
        else return mat_mul(bin_pow_mat(base, n, exp - 1), base, n, n, n);
    }

    public static long[] transform(long[] transformation, long[] vec, int m, int n) {
        long[] result = new long[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += transformation[i * n + j] * vec[j];
            }
        }
        return result;
    }

    // The Karatsuba algorithm is a fast multiplication algorithm
    public static BigInteger karatsuba_mul(BigInteger x, BigInteger y) {
        // cutoff to brute force
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 2000) return x.multiply(y);                // optimize this parameter

        // number of bits divided by 2, rounded up
        N = (N / 2) + (N % 2);

        // x = a + 2^N b,   y = c + 2^N d
        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        // compute sub-expressions
        BigInteger ac = karatsuba_mul(a, c);
        BigInteger bd = karatsuba_mul(b, d);
        BigInteger abcd = karatsuba_mul(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2 * N));
    }

    /*
     * Hamming weight
     * https://en.wikipedia.org/wiki/Hamming_weight
     * It is thus equivalent to the Hamming distance from the all-zero string of the same length
     */
    public static long count_bits(long a) {
        // obtain bitcout of each 2 bit
        a = ((a >> 1) & 0x5555555555555555L) + (a & 0x5555555555555555L);
        // obtain sum bitcout of each 2 group of 2 bit, (each overall 4bit)
        a = ((a >> 2) & 0x3333333333333333L) + (a & 0x3333333333333333L);
        // obtain sum bitcout of each 2 group of 4 bit, (each overall 8bit)
        a = ((a >> 4) & 0x0f0f0f0f0f0f0f0fL) + (a & 0x0f0f0f0f0f0f0f0fL);
        // obtain sum bitcout of each 2 group of 8 bit, (each overall 16bit)
        a = ((a >> 8) & 0x00ff00ff00ff00ffL) + (a & 0x00ff00ff00ff00ffL);
        // and so on..
        a = ((a >> 16) & 0x0000ffff0000ffffL) + (a & 0x0000ffff0000ffffL);
        a = ((a >> 32) & 0x00000000ffffffffL) + (a & 0x00000000ffffffffL);
        return a;
    }

    public static int highestOneBit(int i) {
        i |= (i >> 1);
        i |= (i >> 2);
        i |= (i >> 4);
        i |= (i >> 8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }

    // http://homepage.divms.uiowa.edu/~jones/bcd/mod.shtml
    // we can compute a mod 3 as "a mod 3 = ((a/4) + (a mod 4)) mod 3"
    public static int mod3(int a) {
        a = (a >> 16) + (a & 0xFFFF); /* sum base 2**16 digits
								  a <= 0x1FFFE */
        a = (a >> 8) + (a & 0xFF); /* sum base 2**8 digits
								 a <= 0x2FD */
        a = (a >> 4) + (a & 0xF); /* sum base 2**4 digits
								 a <= 0x3C; worst case 0x3B */
        a = (a >> 2) + (a & 0x3); /* sum base 2**2 digits
								 a <= 0x1D; worst case 0x1B */
        a = (a >> 2) + (a & 0x3); /* sum base 2**2 digits
								 a <= 0x9; worst case 0x7 */
        a = (a >> 2) + (a & 0x3); /* sum base 2**2 digits
								 a <= 0x4 */
        if (a > 2) a = a - 3;
        return a;
    }
}
