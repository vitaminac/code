package core.util;

public class StringUtil {
    public static String longestCommonSubstring(final String string1, final String string2) {
        if (string1.length() > string2.length()) return longestCommonSubstring(string2, string1);
        final int m = string1.length(), n = string2.length();

        final var cs1 = string1.toCharArray();
        final var cs2 = string2.toCharArray();

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
}
