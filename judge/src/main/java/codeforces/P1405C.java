package codeforces;

import java.util.Scanner;

public class P1405C {
    private static boolean checkEqualityForSameCongruenceClass(char[] s, int n, int n_groups) {
        for (int clazz = 0; clazz < n_groups; clazz++) {
            for (int j = clazz; j < n; j += n_groups) {
                if (s[j] != '?') {
                    if (s[clazz] == '?') {
                        // assign value for the first sliding window check
                        s[clazz] = s[j];
                    } else if (s[clazz] != s[j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean checkFirstSlidingWindow(char[] s, int k) {
        int one = 0;
        int zero = 0;
        for (int i = 0; i < k; i++) {
            if (s[i] == '1') one++;
            else if (s[i] == '0') zero++;
        }
        return one <= k / 2 && zero <= k / 2;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            for (int t = sc.nextInt(); t > 0; t--) {
                int n = sc.nextInt();
                int k = sc.nextInt();
                char[] s = sc.next().toCharArray();
                System.out.println(
                        checkEqualityForSameCongruenceClass(s, n, k) && checkFirstSlidingWindow(s, k)
                                ? "YES" : "NO");
            }
        }
    }
}
