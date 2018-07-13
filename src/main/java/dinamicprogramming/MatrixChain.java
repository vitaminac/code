package dinamicprogramming;

public class MatrixChain {
    private final int[] matrices;
    private final int[][] solutions;
    private final int[][] subProblems;
    private int n;

    public MatrixChain(int[] matrices) {
        this.matrices = matrices;
        final int length = matrices.length;
        this.n = length - 1;
        this.solutions = new int[length][length];
        this.subProblems = new int[length][length];
    }

    public int solve() {
        // l is the chain length
        for (int l = 2; l <= n; l++) {
            for (int i = 1, end = n - l + 1; i <= end; i++) {
                int j = i + l - 1;
                this.subProblems[i][j] = Integer.MAX_VALUE;
                for (int k = i, endK = j - 1; k <= endK; k++) {
                    int q = this.subProblems[i][k] + this.subProblems[k + 1][j] + this.matrices[i - 1] * this.matrices[k] * this.matrices[j];
                    if (q < this.subProblems[i][j]) {
                        this.subProblems[i][j] = q;
                        this.solutions[i][j] = k;
                    }
                }
            }
        }
        return this.subProblems[1][n];
    }

    @Override
    public String toString() {
        return this.solution(1, n);
    }

    private String solution(int i, int j) {
        if (i < j) {
            int k = this.solutions[i][j];
            return i + " to " + j + " divide at " + k + "\n" + this.solution(i, k) + this
                    .solution(k + 1, j);
        }
        return "";
    }
}
