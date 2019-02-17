package code.leetcode;

import java.util.Arrays;

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

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        new RotateImage().rotate(matrix);
        System.out.println(Arrays.deepToString(matrix));

        matrix = new int[][]{
                {5, 1, 9, 11},
                {2, 4, 8, 10},
                {13, 3, 6, 7},
                {15, 14, 12, 16}
        };
        new RotateImage().rotate(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }
}
