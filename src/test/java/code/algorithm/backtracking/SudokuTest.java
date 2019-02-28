package code.algorithm.backtracking;

import java.io.IOException;

public class SudokuTest {
    public static void main(String[] args) {
        try {
            final Sudoku sudoku = new Sudoku();
            sudoku.solve();
            System.out.println(sudoku);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}