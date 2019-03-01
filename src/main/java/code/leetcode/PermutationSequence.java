package code.leetcode;

/**
 * https://leetcode.com/problems/permutation-sequence/
 */
public class PermutationSequence {
    public String getPermutation(int n, int k) {
        int[] factorials = new int[n];
        boolean[] used = new boolean[n];
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
            while (used[digit]) ++digit;
            for (int j = 0; j < nth; j++) {
                ++digit;
                while (used[digit]) ++digit;
            }
            used[digit] = true;
            sb.append(digit + 1);
        }
        return sb.toString();
    }
}
