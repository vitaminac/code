package geeksforgeeks.dp;

import core.util.StringUtil;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/longest-common-substring-dp-29/
 * https://www.geeksforgeeks.org/problems/longest-common-substring1452/1
 */
public class LongestCommonSubstring {
    static class Solution {
        public int longestCommonSubstr(final String text1, final String text2, final int m, final int n) {
            return StringUtil.longestCommonSubstring(text1, text2).length();
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
