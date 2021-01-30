package code.algorithm.backtracking;

import code.algorithm.common.SolutionNode;
import core.Queue;

public class Sudoku implements SolutionNode<Sudoku> {
    private final int table[][];
    private final int k;
    private final int value;

    public Sudoku(int[][] table, int k, int value) {
        this.table = table;
        this.k = k;
        this.value = value;
    }

    public void setValue(int k, int value) {
        this.table[k / 9][k % 9] = value;
    }

    public int getValue(int k) {
        return this.table[k / 9][k % 9];
    }

    @Override
    public String toString() {
        this.setValue(this.k, this.value);
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

    @Override
    public boolean isSolution() {
        return this.nextK() == 9 * 9;
    }

    @Override
    public boolean isFeasible() {
        for (int i = 0; i < 9; i++) {
            int k = this.k / 9 * 9 + i;
            if (k != this.k && this.getValue(k) == this.value) {
                return false;
            }
            k = i * 9 + this.k % 9;
            if (k != this.k && this.getValue(k) == this.value) {
                return false;
            }
            k = (this.k / 9 / 3 * 3 * 9) + (this.k % 9 / 3 * 3) + i / 3 * 9 + i % 3;
            if (k != this.k && this.getValue(k) == this.value) {
                return false;
            }
        }
        return true;
    }

    private int nextK() {
        int k = this.k;
        do {
            k++;
        } while (k < 9 * 9 && this.getValue(k) > 0);
        return k;
    }

    @Override
    public Queue<Sudoku> branch() {
        Queue<Sudoku> queue = Queue.fromSinglyLinkedListDoubleReference();
        this.setValue(this.k, this.value);
        for (int i = 1; i <= 9; i++) {
            queue.enqueue(new Sudoku(this.table, this.nextK(), i));
        }
        return queue;
    }

    @Override
    public void backtrack() {
        this.setValue(this.k, 0);
    }
}
