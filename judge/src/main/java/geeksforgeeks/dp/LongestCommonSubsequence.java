package geeksforgeeks.dp;

import core.util.StringUtil;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/
 * https://www.geeksforgeeks.org/problems/longest-common-subsequence-1587115620/1
 */
public class LongestCommonSubsequence {
    // Function to find the length of longest common subsequence in two strings.
    static int lcs(int n, int m, String str1, String str2) {
        return StringUtil.longestCommonSubsequence(str1, str2).length();
    }

    public static void main(String[] args) {
        try (final Scanner sc = new Scanner(System.in)) {
            int numberOfTestCases = sc.nextInt();
            for (int i = 0; i < numberOfTestCases; i++) {
                final String s1 = sc.next();
                final String s2 = sc.next();
                System.out.println(lcs(s1.length(), s2.length(), s1, s2));
            }
        }
    }
}
