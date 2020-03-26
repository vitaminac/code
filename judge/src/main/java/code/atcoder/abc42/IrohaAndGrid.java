package code.atcoder.abc42;

import java.util.Scanner;

public class IrohaAndGrid {
    private static long pow_mod(long base, long exp, long modulo) {
        long result = 1;
        base = base % modulo;
        while (exp > 0) {
            if ((exp & 0x1) != 0) {
                result = (result * base) % modulo;
            }
            base = (base * base) % modulo;
            exp >>= 1;
        }
        return result;
    }

    private static final int MOD = 1000000007;
    private static final int MAX_N = 200005;
    private static final long[] MODULAR_INVERSE = new long[MAX_N];
    private static final long[] FACTORIAL = new long[MAX_N];

    static {
        FACTORIAL[0] = 1;
        for (int i = 1; i < MAX_N; i++) FACTORIAL[i] = FACTORIAL[i - 1] * i % MOD;
        // in the special case where m is a prime
        // a modular inverse is given by a^{-1} = a^{m-2} mod m
        MODULAR_INVERSE[MAX_N - 1] = pow_mod(FACTORIAL[MAX_N - 1], MOD - 2, MOD);
        // same as divide FACTORIAL[0]
        MODULAR_INVERSE[0] = 1;
        // 1^{-1} = 1 mod p
        // if b_n = ∏_{i=0}^{n} a_i, compute b_n^{-1}
        // then from n down to 2: b_{i-1}^{-1} = b_i^{-1} * a_i
        for (int i = MAX_N - 1; i >= 2; i--) MODULAR_INVERSE[i - 1] = MODULAR_INVERSE[i] * (i) % MOD;
    }

    private static long binomial_coefficient(int n, int r) {
        return FACTORIAL[n] * MODULAR_INVERSE[r] % MOD * MODULAR_INVERSE[n - r] % MOD;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int H = sc.nextInt();
            int W = sc.nextInt();
            int A = sc.nextInt();
            int B = sc.nextInt();
            long ans = 0;
            /*
             * |S|Y|Y|
             * |X| |E|
             */
            for (int i = B + 1; i <= W; i++) {
                int x = (i - 1); // the number of horizontal step can move from start to one of the yellow square
                int y = (H - A - 1); // the number of vertical step can move from start to one of the yellow square
                int a = W - i; // the number of horizontal step can move from one of the yellow square to end
                int b = A - 1; // the number of vertical step can move from one of the yellow square to end
                ans += binomial_coefficient(x + y, x) * binomial_coefficient(a + b, a) % MOD;
                ans %= MOD;
            }
            System.out.println(ans);
        }
    }
}
