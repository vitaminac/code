package code.algorithm.backtracking;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Sudoku {
    private int table[][] = new int[9][9];

    public Sudoku() throws IOException {
        try (Scanner scanner = new Scanner(new InputStreamReader(System.in))) {
            int i = 0;
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    this.setValue(i / 9, i % 9, scanner.nextInt());
                    ++i;
                } else {
                    scanner.next();
                }
            }
        }
    }

    private void setValue(int row, int col, int value) {
        this.table[row][col] = value;
    }

    private int getValue(int row, int col) {
        return this.table[row][col];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2 * 9; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < 9; j++) {
                    sb.append(" ");
                    sb.append("-");
                }
            } else {
                sb.append("|");
                for (int j = 0; j < 9; j++) {
                    if (this.table[i / 2][j] > 0 && this.table[i / 2][j] < 10) {
                        sb.append(this.table[i / 2][j]);
                    } else {
                        sb.append(" ");
                    }
                    sb.append("|");
                }
            }
            sb.append('\n');
        }
        for (int j = 0; j < 9; j++) {
            sb.append(" ");
            sb.append("-");
        }
        return sb.toString();
    }

    private boolean isPromising(int row, int col, int value) {
        for (int i = 0; i < 9; i++) {
            if (this.getValue(i, col) == value) {
                return false;
            }
        }
        for (int j = 0; j < 9; j++) {
            if (this.getValue(row, j) == value) {
                return false;
            }
        }
        for (int i = (row / 3) * 3; i < (row / 3 + 1) * 3; i++) {
            for (int j = (col / 3) * 3; j < (col / 3 + 1) * 3; j++) {
                if (this.getValue(i, j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean solve(int k) {
        // si es solucion
        if (k >= 9 * 9) {
            return true;
        } else {
            int row = k / 9;
            int col = k % 9;
            if (this.getValue(row, col) > 0 && this.getValue(row, col) < 10) {
                return this.solve(k + 1);
            } else {
                for (int i = 1; i < 10; i++) {
                    // si es prometedor
                    if (this.isPromising(row, col, i)) {
                        this.setValue(row, col, i);
                        if (this.solve(k + 1)) {
                            return true;
                        }
                    }
                }
                this.setValue(row, col, 0);
                return false;
            }
        }
    }

    public void solve() {
        this.solve(0);
    }
}
