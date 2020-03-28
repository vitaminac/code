package cp_algorithms.algebra;

import code.adt.Math;

// https://cp-algorithms.com/algebra/binary-exp.html
public class BinaryExponentiation {
    public static long binpow(long base, long exp, long mod) {
        return Math.bin_pow_mod(base, exp, mod);
    }

    public static long fibonacci(int n) {
        long[] mat = Math.bin_pow_mat(new long[]{0, 1, 1, 1}, 2, n);
        return mat[2];
    }

    public static long permutation(long n, int k) {
        return Math.permutation(n, k);
    }

    public static long[] k_transform(long[] transformation, long[] vec, int n, int k) {
        return Math.transform(Math.bin_pow_mat(transformation, n, k), vec, n, n);
    }
}
