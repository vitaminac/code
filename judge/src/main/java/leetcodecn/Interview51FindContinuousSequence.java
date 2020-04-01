package leetcodecn;

import java.util.ArrayList;
import java.util.List;

public class Interview51FindContinuousSequence {
    public int[][] findContinuousSequence(int target) {
        List<int[]> ans = new ArrayList<>();
        int i = 1, j = 2, s = 3;
        while ((i + j) <= target) {
            if (s > target) {
                s -= i;
                i += 1;
            } else if (s < target) {
                j += 1;
                s += j;
            } else {
                int[] arr = new int[j - i + 1];
                for (int k = i; k <= j; k++) {
                    arr[k - i] = k;
                }
                ans.add(arr);
                j += 1;
                s += j;
            }
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
