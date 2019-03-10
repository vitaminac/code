package code.algorithm.backtracking;

import java.util.Iterator;

public class BacktrackingSudoku extends Backtracking<Sudoku> {
    @Override
    public boolean isSolution(Sudoku sudoku) {
        return sudoku.getK() + 1 == 9 * 9;
    }

    @Override
    public Iterator<Sudoku> expand(Sudoku partialSolution) {
        return new Iterator<Sudoku>() {
            private int value = 0;
            private int lastK;

            {
                this.lastK = partialSolution.getK();
                do {
                    partialSolution.setK(partialSolution.getK() + 1);
                } while (partialSolution.getK() + 1 < 9 * 9 && partialSolution.getValue(partialSolution.getK()) > 0);
            }

            @Override
            public boolean hasNext() {
                if (this.value < 9 && partialSolution.getK() < 81) {
                    return true;
                } else {
                    this.undo();
                    return false;
                }
            }

            @Override
            public Sudoku next() {
                partialSolution.setValue(partialSolution.getK(), ++this.value);
                return partialSolution;
            }

            public void undo() {
                partialSolution.setValue(partialSolution.getK(), 0);
                partialSolution.setK(this.lastK);
            }
        };
    }

    @Override
    public boolean isFeasible(Sudoku partialSolution) {
        int value = partialSolution.getValue(partialSolution.getK());
        int k;
        for (int i = 0; i < 9; i++) {
            k = partialSolution.getK() / 9 * 9 + i;
            if (k != partialSolution.getK() && partialSolution.getValue(k) == value) {
                return false;
            }
            k = i * 9 + partialSolution.getK() % 9;
            if (k != partialSolution.getK() && partialSolution.getValue(k) == value) {
                return false;
            }
            k = (partialSolution.getK() / 9 / 3 * 3 * 9) + (partialSolution.getK() % 9 / 3 * 3) + i / 3 * 9 + i % 3;
            if (k != partialSolution.getK() && partialSolution.getValue(k) == value) {
                return false;
            }
        }
        return true;
    }
}
