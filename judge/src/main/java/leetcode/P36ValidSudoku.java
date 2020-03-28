package leetcode;

public class P36ValidSudoku {
    boolean isValidSudoku(char[][] sudoku) {
        int bitmapH, bitmapV, bitmapB;
        for (int i = 0; i < 9; i++) {
            bitmapH = 0;
            bitmapV = 0;
            bitmapB = 0;
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j] >= '1' && sudoku[i][j] <= '9') {
                    if ((bitmapH & (1 << (sudoku[i][j] - '0'))) != 0) {
                        return false;
                    } else {
                        bitmapH |= 1 << (sudoku[i][j] - '0');
                    }
                }
                if (sudoku[j][i] >= '1' && sudoku[j][i] <= '9') {
                    if ((bitmapV & (1 << (sudoku[j][i] - '0'))) != 0) {
                        return false;
                    } else {
                        bitmapV |= 1 << (sudoku[j][i] - '0');
                    }
                }
                int row = ((i / 3) * 3) + (j / 3);
                int col = ((i % 3) * 3) + (j % 3);
                if (sudoku[row][col] >= '1' && sudoku[row][col] <= '9') {
                    if ((bitmapB & (1 << (sudoku[row][col] - '0'))) != 0) {
                        return false;
                    } else {
                        bitmapB |= 1 << (sudoku[row][col] - '0');
                    }
                }
            }
        }
        return true;
    }
}

