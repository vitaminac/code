package code.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// https://leetcode.com/problems/two-sum/
public class TwoSum {
    private static class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                if (map.containsKey(complement)) {
                    return new int[]{map.get(complement), i};
                }
                map.put(nums[i], i);
            }
            throw new IllegalArgumentException("No two sum solution");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final Solution sol = new Solution();
        int target = scanner.nextInt();
        int n = scanner.nextInt();
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = scanner.nextInt();
        }
        final int[] indices = sol.twoSum(num, target);
        System.out.println(indices[0] + " " + indices[1]);
    }
}
