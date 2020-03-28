package leetcode.dp;

/* https://leetcode.com/problems/guess-number-higher-or-lower-ii */
public class GuessNumberHigherOrLowerII {
    public int getMoneyAmount(int n) {
        int[][] table = new int[n + 1][n + 1];
        for (int j = 2; j <= n; j++) {
            table[j - 1][j] = j - 1;
            for (int i = j - 2; i > 0; i--) {
                int globalMin = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    globalMin = Math.min(globalMin, k + Math.max(table[i][k - 1], table[k + 1][j]));
                }
                table[i][j] = globalMin;
            }
        }
        return table[1][n];
    }
}
