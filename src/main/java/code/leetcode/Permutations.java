package code.leetcode;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * https://leetcode.com/problems/permutations/
 */
public class Permutations {
    private static int factorial(int n) {
        int res = 1, i;
        for (i = 2; i <= n; i++)
            res *= i;
        return res;
    }

    public List<List<Integer>> permute(int[] nums) {
        List<Map.Entry<List<Integer>, Set<Integer>>> result = new LinkedList<>();
        Set<Integer> set = new TreeSet<>();
        Arrays.stream(nums).forEach(set::add);
        result.add(new AbstractMap.SimpleImmutableEntry<>(new ArrayList<>(), set));

        int n_permutation = factorial(nums.length);
        while (result.size() < n_permutation) {
            List<Map.Entry<List<Integer>, Set<Integer>>> newResult = new LinkedList<>();
            for (Map.Entry<List<Integer>, Set<Integer>> entry : result) {
                final Set<Integer> value = entry.getValue();
                for (int number : value) {
                    final ArrayList<Integer> integers = new ArrayList<>(entry.getKey());
                    integers.add(number);
                    final Set<Integer> unused = new TreeSet<>(value);
                    unused.remove(number);
                    newResult.add(new AbstractMap.SimpleImmutableEntry<>(integers, unused));
                }
            }
            result = newResult;
        }
        List<List<Integer>> returnVal = new ArrayList<>();
        for (Map.Entry<List<Integer>, Set<Integer>> entry : result) {
            final List<Integer> list = entry.getKey();
            list.addAll(entry.getValue());
            returnVal.add(list);
        }
        return returnVal;
    }
}
