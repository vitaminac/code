package code.leetcode;

/**
 * https://leetcode.com/problems/permutation-sequence/
 */
public class PermutationSequence {
    public String getPermutation(int n, int k) {
        int[] factorials = new int[n];
        char[] digits = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        factorials[0] = 1;
        for (int i = 1; i < n; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        StringBuilder sb = new StringBuilder();
        k = k - 1;
        for (int i = n - 1; i >= 0; i--) {
            int nth = k / factorials[i];
            k = k % factorials[i];
            int digit = 0;
            while (digits[digit] == 0) ++digit;
            for (int j = 0; j < nth; j++) {
                ++digit;
                while (digits[digit] == 0) ++digit;
            }
            sb.append(digits[digit]);
            digits[digit] = 0;
        }
        return sb.toString();
    }
}
