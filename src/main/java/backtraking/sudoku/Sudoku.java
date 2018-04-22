package backtraking.sudoku;

import java.util.HashSet;

public class Sudoku {
    private int table[][] = new int[9][9];

    public void setValue(int i, int j, int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException();
        }
        this.table[i][j] = value;
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
            sb.append(System.lineSeparator());
        }
        for (int j = 0; j < 9; j++) {
            sb.append(" ");
            sb.append("-");
        }
        return sb.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }

    public HashSet<Integer> getChoices(int x, int y) {
        HashSet<Integer> candidate = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            candidate.add(i);
        }
        for (int i = 0; i < 9; i++) {
            candidate.remove(this.table[i][y]);
        }
        for (int j = 0; j < 9; j++) {
            candidate.remove(this.table[x][j]);
        }
        for (int i = (x / 3) * 3; i < (x / 3 + 1) * 3; i++) {
            for (int j = (y / 3) * 3; j < (y / 3 + 1) * 3; j++) {
                candidate.remove(this.table[i][j]);
            }
        }
        return candidate;
    }

    public boolean findSolution(int i, int j) {
        for (; i < 9; i++) {
            for (; j < 9; j++) {
                if (this.table[i][j] < 1 || this.table[i][j] > 9) {
                    final HashSet<Integer> choices = this.getChoices(i, j);
                    for (int c : choices) {
                        this.setValue(i, j, c);
                        if (this.findSolution(i, j)) {
                            return true;
                        }
                    }
                    this.table[i][j] = 0;
                    return false;
                }
            }
            j = 0;
        }
        return true;
    }

    public boolean findSolution() {
        return this.findSolution(0, 0);
    }
}
