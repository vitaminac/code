package code.algorithm.backtracking;

import java.io.InputStreamReader;
import java.util.Scanner;

public class BacktrackingSudokuTest {
    public static void main(String[] args) {
        int[][] table = new int[9][9];
        try (Scanner scanner = new Scanner(new InputStreamReader(System.in))) {
            int i = 0;
            while (scanner.hasNextInt()) {
                table[i / 9][i % 9] = scanner.nextInt();
                ++i;
            }
        }
        final Sudoku sudoku = new Sudoku(table, -1);
        final BacktrackingSudoku backtrackingSudoku = new BacktrackingSudoku();
        System.out.println(backtrackingSudoku.solve(sudoku));
    }
}