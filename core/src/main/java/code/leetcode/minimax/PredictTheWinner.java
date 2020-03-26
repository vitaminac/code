package code.leetcode.minimax;

public class PredictTheWinner {
    public boolean PredictTheWinner(int[] nums) {
        int[] dp = new int[nums.length];
        for (int len = nums.length; len >= 0; len--) {
            for (int e = len + 1; e < nums.length; e++) {
                int a = nums[len] - dp[e];
                int b = nums[e] - dp[e - 1];
                dp[e] = Math.max(a, b);
            }
        }
        return dp[nums.length - 1] >= 0;
    }
}
