package geeksforgeeks.dp;

import core.util.StringUtil;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/lcs-longest-common-subsequence-three-strings/
 * https://www.geeksforgeeks.org/problems/lcs-of-three-strings0028/1
 */
public class LongestCommonSubsequenceOfThreeStrings {
    static class Solution {
        int LCSof3(String A, String B, String C, int n1, int n2, int n3) {
            return StringUtil.longestCommonSubsequence(A, B, C).length();
        }
    }

    public static void main(String[] args) {
        try (final Scanner sc = new Scanner(System.in)) {
            int numberOfTestCases = sc.nextInt();
            final var solution = new Solution();
            for (int i = 0; i < numberOfTestCases; i++) {
                final String s1 = sc.next();
                final String s2 = sc.next();
                final String s3 = sc.next();
                System.out.println(solution.LCSof3(s1, s2, s3, s1.length(), s2.length(), s3.length()));
            }
        }
    }
}
