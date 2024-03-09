package geeksforgeeks.dp;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/longest-common-substring-dp-29/
 * https://www.geeksforgeeks.org/problems/longest-common-substring1452/1
 */
public class LongestCommonSubstring {
    static class Solution {
        public int longestCommonSubstr(final String text1, final String text2, final int m, final int n) {
            if (m > n) return longestCommonSubstr(text2, text1, n, m);

            final char[] s1 = text1.toCharArray(), s2 = text2.toCharArray();

            // mex length of longest common substring (LCS)
            int max = 0;
            // length of LCS of ended at j-th character of s2 with s1 when length of substring is i
            final int[] dp = new int[n + 1];

            for (int i = 1; i <= m; i++) {
                // length of LCS of ended at j - 1 character of s2 with s1 when length of substring is i - 1
                int prev = 0;
                for (int j = 1; j <= n; j++) {
                    int tmp = dp[j];
                    if (s1[i - 1] == s2[j - 1]) {
                        // if i-th character of s1 corresponds to j-th character of s2
                        // then add one to the length of LCS of ended at j - 1 character of s2 with s1 when length of substring is i - 1
                        dp[j] = prev + 1;
                        max = Math.max(max, dp[j]);
                    } else {
                        // otherwise length of LCS of ended at j character of s2 with s1 is 0
                        dp[j] = 0;
                    }
                    prev = tmp;
                }
            }
            return max;
        }
    }

    public static void main(String[] args) {
        try (final Scanner sc = new Scanner(System.in)) {
            int numberOfTestCases = sc.nextInt();
            final var solution = new Solution();
            for (int i = 0; i < numberOfTestCases; i++) {
                final String s1 = sc.next();
                final String s2 = sc.next();
                System.out.println(solution.longestCommonSubstr(s1, s2, s1.length(), s2.length()));
            }
        }
    }
}
