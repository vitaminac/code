package backtraking.sudoku;

import org.junit.Test;

public class SudokuTest {

    @Test
    public void print() {
        Sudoku sudoku = new Sudoku();
        sudoku.setValue(0, 0, 5);
        sudoku.setValue(0, 1, 3);
        sudoku.setValue(0, 4, 7);
        sudoku.setValue(1, 0, 6);
        sudoku.setValue(1, 3, 1);
        sudoku.setValue(1, 4, 9);
        sudoku.setValue(1, 5, 5);
        sudoku.setValue(2, 1, 9);
        sudoku.setValue(2, 2, 8);
        sudoku.setValue(2, 7, 6);
        sudoku.setValue(3, 0, 8);
        sudoku.setValue(3, 4, 6);
        sudoku.setValue(3, 8, 3);
        sudoku.setValue(4, 0, 4);
        sudoku.setValue(4, 3, 8);
        sudoku.setValue(4, 5, 3);
        sudoku.setValue(4, 8, 1);
        sudoku.setValue(5, 0, 7);
        sudoku.setValue(5, 4, 2);
        sudoku.setValue(5, 8, 6);
        sudoku.setValue(6, 1, 6);
        sudoku.setValue(6, 6, 2);
        sudoku.setValue(6, 7, 8);
        sudoku.setValue(7, 3, 4);
        sudoku.setValue(7, 4, 1);
        sudoku.setValue(7, 5, 9);
        sudoku.setValue(7, 8, 5);
        sudoku.setValue(8, 4, 8);
        sudoku.setValue(8, 7, 7);
        sudoku.setValue(8, 8, 9);
        sudoku.findSolution();
        sudoku.print();
    }
}