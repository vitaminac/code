package code;

import java.util.Scanner;

public class StdIn {
    private static Scanner scanner = new Scanner(System.in);

    public static int[][] readIntMatrix() {
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    public static int readInt() {
        return scanner.nextInt();
    }
}
