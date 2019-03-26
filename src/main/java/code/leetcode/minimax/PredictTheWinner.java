package code.leetcode.minimax;

public class PredictTheWinner {
    private static int max(int[] nums, int left, int right, int turn) {
        if (left == right) return turn * nums[left];
        return turn * Math.max( // do maximum when turn is negative
                turn * (turn * nums[left] + max(nums, left + 1, right, -turn)),
                turn * (turn * nums[right] + max(nums, left, right - 1, -turn))
        );
    }

    public boolean PredictTheWinner(int[] nums) {
        return this.max(nums, 0, nums.length - 1, 1) >= 0;
    }
}
