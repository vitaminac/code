package leetcodecn;

public class Interview61IsStraight {
    public boolean isStraight(int[] nums) {
        boolean[] visited = new boolean[14];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int n : nums) {
            if (n == 0) continue;
            if (visited[n]) return false;
            visited[n] = true;
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        return max - min + 1 <= 5;
    }
}
