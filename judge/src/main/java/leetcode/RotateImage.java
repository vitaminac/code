package leetcode;

/**
 * https://leetcode.com/problems/rotate-image/
 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int half = n / 2;
        for (int i = 0; i < half; i++) {
            int j = n - 1 - i;
            for (int p = matrix.length - 2 - i; p >= i; p--) {
                int q = n - 1 - p;
                int temp = matrix[i][p];
                matrix[i][p] = matrix[q][i];
                matrix[q][i] = matrix[j][q];
                matrix[j][q] = matrix[p][j];
                matrix[p][j] = temp;
            }
        }
    }
}
