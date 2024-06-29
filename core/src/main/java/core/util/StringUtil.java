package core.util;

public class StringUtil {
    public static String longestCommonSubstring(final String s1, final String s2) {
        if (s1.length() > s2.length()) return longestCommonSubstring(s2, s1);
        final int m = s1.length(), n = s2.length();

        final var cs1 = s1.toCharArray();
        final var cs2 = s2.toCharArray();

        // length of longest common substring (LCS)
        int max_len = 0;
        // index of s2 that ends LCS
        int max_j = 0;
        // length of LCS that ends at i character of s1 and ends at j-th character of s2
        final int[] dp = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            // length of LCS that ends at i - 1 character of s1 and ends at j - 1 character of s2
            int prev = 0;
            for (int j = 1; j <= n; j++) {
                int tmp = dp[j];
                if (cs1[i - 1] == cs2[j - 1]) {
                    // if i-th character of s1 corresponds to j-th character of s2
                    // then add one to the length of LCS that ends at i - 1 character of s1 and ends at j - 1 character of s2
                    dp[j] = prev + 1;
                    if (dp[j] > max_len) {
                        max_len = dp[j];
                        max_j = j;
                    }
                } else {
                    // otherwise length of LCS that ends at i - 1 character of s1 and ends at j - 1 character of s2 is 0
                    dp[j] = 0;
                }
                prev = tmp;
            }
        }
        return new String(cs2, max_j - max_len, max_len);
    }

    public static String longestCommonSubsequence(final String s1, final String s2) {
        final char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
        int m = cs1.length, n = cs2.length;
        // length of longest common sequence (LCS) of first i characters of s1 to first j characters of s2
        final var dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (cs1[i - 1] == cs2[j - 1]) {
                    // if i-th character of s1 corresponds to j-th character of s2
                    // then add one to the length of LCS of
                    // first i - 1 characters of s1 to first j - 1 characters of s2
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // otherwise length of LCS of first i characters of s1 to first j characters of s2 is the maximum of
                    // length of LCS of first i - 1 characters of s1 to first j characters of s2 by removing i-th character of s1 or
                    // length of LCS of first i characters of s1 to first j - 1 characters of s2 by removing j-th character of s2
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        final StringBuilder sb = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (dp[i][j] > dp[i - 1][j] && dp[i][j] > dp[i][j - 1]) {
                sb.append(cs1[i - 1]);
                i -= 1;
                j -= 1;
            } else if (dp[i][j] == dp[i - 1][j]) {
                i -= 1;
            } else {
                j -= 1;
            }
        }
        sb.reverse();
        return sb.toString();
    }
}
