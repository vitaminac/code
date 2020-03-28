package leetcode;

import java.util.Arrays;

public class RotateImageTest {
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