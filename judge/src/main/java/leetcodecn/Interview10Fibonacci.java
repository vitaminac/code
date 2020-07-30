package leetcodecn;

public class Interview10Fibonacci {
    public int fib(int n) {
        int f1 = 0, f2 = 1;
        for (int i = 0; i < n; i++) {
            int sum = (f1 + f2) % 1000000007;
            f1 = f2;
            f2 = sum;
        }
        return f1;
    }
}
