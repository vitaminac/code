package leetcode;

public class P37SudokuSolver {
}

class Solution {
    private boolean isValid(char[][] sudoku, int i, int j) {
        boolean[] bitmapH = new boolean[9];
        boolean[] bitmapV = new boolean[9];
        boolean[] bitmapB = new boolean[9];
        for (int k = 0; k < 9; k++) {
            if (sudoku[i][k] >= '1' && sudoku[i][k] <= '9') {
                if (bitmapH[sudoku[i][k] - '1']) {
                    return false;
                } else {
                    bitmapH[sudoku[i][k] - '1'] = true;
                }
            }
            if (sudoku[k][j] >= '1' && sudoku[k][j] <= '9') {
                if (bitmapV[sudoku[k][j] - '1']) {
                    return false;
                } else {
                    bitmapV[sudoku[k][j] - '1'] = true;
                }
            }
            int row = ((i / 3) * 3) + (k / 3);
            int col = ((j / 3) * 3) + (k % 3);
            if (sudoku[row][col] >= '1' && sudoku[row][col] <= '9') {
                if (bitmapB[sudoku[row][col] - '1']) {
                    return false;
                } else {
                    bitmapB[sudoku[row][col] - '1'] = true;
                }
            }
        }
        return true;
    }

    public boolean solveSudoku(char[][] board, int i, int j) {
        if (i == 9) return true;
        if (board[i][j] == '.') {
            for (int k = 1; k <= 9; k++) {
                board[i][j] = (char) ('0' + k);
                if (this.isValid(board, i, j) && (solveSudoku(board, i + ((j + 1) / 9), (j + 1) % 9))) {
                    return true;
                }
            }
            board[i][j] = '.';
            return false;
        } else {
            return solveSudoku(board, i + ((j + 1) / 9), (j + 1) % 9);
        }
    }

    public void solveSudoku(char[][] board) {
        solveSudoku(board, 0, 0);
    }
}