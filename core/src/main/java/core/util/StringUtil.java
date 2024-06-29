package core.util;

import java.util.Arrays;

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

    public static class LongestCommonSubsequenceResult {
        private final char[][] charArrays;
        private final int[] dp;

        private LongestCommonSubsequenceResult(final char[][] charArrays,
                                               final int[] dp) {
            this.charArrays = charArrays;
            this.dp = dp;
        }

        public int length() {
            final int[] indices = Arrays.stream(charArrays).mapToInt(arr -> arr.length).toArray();
            return dp[computeIndex(indices, charArrays)];
        }

        @Override
        public String toString() {
            // backtrack to find the string value
            final StringBuilder sb = new StringBuilder();
            final int[] indices = Arrays.stream(charArrays).mapToInt(arr -> arr.length).toArray();
            while (allIndicesGreaterThanZero(indices)) {
                if (isSameCharacter(indices, charArrays)) {
                    for (int i = 0; i < indices.length; i++) indices[i] -= 1;
                    sb.append(charArrays[0][indices[0]]);
                } else {
                    int currentIndex = computeIndex(indices, charArrays);
                    for (int i = 0; i < indices.length; i++) {
                        indices[i] -= 1;
                        if (dp[currentIndex] == dp[computeIndex(indices, charArrays)]) {
                            break;
                        } else {
                            // restore index
                            indices[i] += 1;
                        }
                    }
                }
            }
            sb.reverse();
            return sb.toString();
        }
    }

    public static LongestCommonSubsequenceResult longestCommonSubsequence(final String... ss) {
        assert ss.length > 1;
        final var charArrays = Arrays.stream(ss).map(String::toCharArray).toArray(char[][]::new);
        // length of longest common sequence (LCS) of first i characters of s1 to first j characters of s2
        final int size = Arrays.stream(ss).mapToInt(String::length).map((value) -> value + 1).reduce(1, (ac, value) -> ac * value);
        final var dp = new int[size];
        final var indices = new int[charArrays.length];
        Arrays.fill(indices, 1);
        int currentIndex;
        while ((currentIndex = computeIndex(indices, charArrays)) < size) {
            if (isSameCharacter(indices, charArrays)) {
                // if i-th character of s1 corresponds to j-th character of s2
                // then add one to the length of LCS of
                // first i - 1 characters of s1 to first j - 1 characters of s2
                int prevIndex = computeIndex(Arrays.stream(indices).map(i -> i - 1).toArray(), charArrays);
                dp[currentIndex] = dp[prevIndex] + 1;
            } else {
                // otherwise length of LCS of first i characters of s1 to first j characters of s2 is the maximum of
                // length of LCS of first i - 1 characters of s1 to first j characters of s2 by removing i-th character of s1 or
                // length of LCS of first i characters of s1 to first j - 1 characters of s2 by removing j-th character of s2
                dp[currentIndex] = maxLengthOfSubLCS(indices, dp, charArrays);
            }
            increment(indices, charArrays);
        }
        return new LongestCommonSubsequenceResult(charArrays, dp);
    }

    private static boolean allIndicesGreaterThanZero(final int[] indices) {
        return Arrays.stream(indices).allMatch(i -> i > 0);
    }

    private static int computeIndex(final int[] indices, final char[][] charArrays) {
        int index = indices[indices.length - 1];
        int multiplier = 1;
        for (int i = indices.length - 2; i >= 0; i--) {
            multiplier *= charArrays[i + 1].length + 1;
            index += indices[i] * multiplier;
        }
        return index;
    }

    private static boolean isSameCharacter(final int[] indices, final char[][] charArrays) {
        for (int i = 1; i < indices.length; i++) {
            if (charArrays[i][indices[i] - 1] != charArrays[i - 1][indices[i - 1] - 1]) return false;
        }
        return true;
    }

    private static int maxLengthOfSubLCS(final int[] indices, final int[] dp, final char[][] charArrays) {
        int max = 0;
        for (int i = 0; i < indices.length; i++) {
            indices[i] -= 1;
            max = Math.max(max, dp[computeIndex(indices, charArrays)]);
            // restore index
            indices[i] += 1;
        }
        return max;
    }

    private static void increment(final int[] indices, final char[][] charArrays) {
        indices[indices.length - 1] += 1;
        for (int i = indices.length - 1; i > 0 && indices[i] > charArrays[i].length; i--) {
            indices[i - 1] += 1;
            indices[i] = 1;
        }
    }
}
