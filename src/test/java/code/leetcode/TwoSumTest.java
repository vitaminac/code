package code.leetcode;

import java.util.Scanner;

public class TwoSumTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final TwoSum sol = new TwoSum();
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