package leetcodecn;

public class Interview13RobotMovingCount {
    private int f(int n) {
        int result = 0;
        while (n > 0) {
            result += n % 10;
            n /= 10;
        }
        return result;
    }

    public int movingCount(int m, int n, int k) {
        boolean[] reachable = new boolean[n];
        reachable[0] = true;
        int result = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (f(i) + f(j) <= k) {
                    reachable[j] = (j != 0 && reachable[j - 1]) || reachable[j];
                    result += reachable[j] ? 1 : 0;
                } else {
                    reachable[j] = false;
                }
            }
        }
        return result;
    }
}
