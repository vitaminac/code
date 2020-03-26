package code.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/permutations/
 */
public class Permutations {
    private void permuteRecursive(List<Integer> numbers, int begin, List<List<Integer>> result) {
        if (begin >= numbers.size()) {
            // one permutation instance
            result.add(new ArrayList<>(numbers));
            return;
        }
        for (int i = begin; i < numbers.size(); i++) {
            int temp = numbers.get(begin);
            numbers.set(begin, numbers.get(i));
            numbers.set(i, temp);
            this.permuteRecursive(numbers, begin + 1, result);
            temp = numbers.get(begin);
            numbers.set(begin, numbers.get(i));
            numbers.set(i, temp);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        this.permuteRecursive(Arrays.stream(nums).boxed().collect(Collectors.toList()), 0, result);
        return result;
    }
}
