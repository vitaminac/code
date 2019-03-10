package code.algorithm.backtracking;

public class Sudoku {
    private int table[][];
    private int k;

    public Sudoku(int[][] table, int k) {
        this.table = table;
        this.k = k;
    }

    public void setValue(int k, int value) {
        this.table[k / 9][k % 9] = value;
    }

    public int getValue(int k) {
        return this.table[k / 9][k % 9];
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

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
}
