package poj;

import java.util.Scanner;

/**
 * http://poj.org/problem?id=1050
 * <p>
 * Given a two-dimensional array of positive and negative integers,
 * a sub-rectangle is any contiguous sub-array of size 1 × 1 or greater located within the whole array.
 * The sum of a rectangle is the sum of all the elements in that rectangle.
 * In this problem the sub-rectangle with the largest sum
 * is referred to as the maximal sub-rectangle.
 * <p>
 * As an example, the maximal sub-rectangle of the array:
 * 0 -2 -7 0
 * 9 2 -6 2
 * -4 1 -4 1
 * -1 8 0 -2
 * is in the lower left corner:
 * 9 2
 * -4 1
 * -1 8
 * and has a sum of 15
 * <p>
 * Input
 * The input consists of an N × N array of integers.
 * The input begins with a single positive integer N on a line by itself,
 * indicating the size of the square two-dimensional array.
 * This is followed by N^2 integers separated by whitespace (spaces and newlines).
 * These are the N^2 integers of the array,
 * presented in row-major order.
 * That is, all numbers in the first row, left to right,
 * then all numbers in the second row, left to right, etc.
 * N may be as large as 100.
 * The numbers in the array will be in the range [-127,127].
 * <p>
 * Output
 * Output the sum of the maximal sub-rectangle.
 * <p>
 * Sample Input
 * 4
 * 0 -2 -7 0 9 2 -6 2
 * -4 1 -4 1 -1
 * 8 0 -2
 * <p>
 * Sample Output
 * 15
 */
public class P1050ToTheMax {
    public static void main(String[] args) throws Exception {
        try (final Scanner sc = new Scanner(System.in)) {
            final int N = sc.nextInt();
            final int[][] I = new int[N + 1][N + 1];
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    I[i][j] = sc.nextInt() + I[i][j - 1] + I[i - 1][j] - I[i - 1][j - 1];
                }
            }

            int max = Integer.MIN_VALUE;
            for (int leftUpperX = 0; leftUpperX < N; leftUpperX++) {
                for (int leftUpperY = 0; leftUpperY < N; leftUpperY++) {
                    for (int rightLowerX = leftUpperX + 1; rightLowerX <= N; rightLowerX++) {
                        for (int rightLowerY = leftUpperY + 1; rightLowerY <= N; rightLowerY++) {
                            final int A = I[leftUpperX][leftUpperY];
                            final int B = I[rightLowerX][leftUpperY];
                            final int C = I[leftUpperX][rightLowerY];
                            final int D = I[rightLowerX][rightLowerY];
                            max = Math.max(max, D + A - B - C);
                        }
                    }
                }
            }
            System.out.println(max);
        }
    }
}
