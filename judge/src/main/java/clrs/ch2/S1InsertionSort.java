package clrs.ch2;

import core.Arrays;

import java.util.Scanner;

public class S1InsertionSort {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int N = sc.nextInt();
                Integer[] nums = new Integer[N];
                for (int i = 0; i < N; i++) {
                    nums[i] = sc.nextInt();
                }
                Arrays.insertion_sort(nums, 0, N - 1, Integer::compare);
                System.out.println(Arrays.toString(nums));
            }
        }
    }
}
