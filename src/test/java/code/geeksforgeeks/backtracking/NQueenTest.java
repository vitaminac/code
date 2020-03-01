package code.geeksforgeeks.backtracking;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class NQueenTest {
    @Test
    public void queen4() {
        int[] solution = NQueen.find(4);
        assertArrayEquals(new int[]{1, 3, 0, 2}, solution);
    }

    @Test
    public void queen8() {
        int[] solution = NQueen.find(8);
        assertArrayEquals(new int[]{0, 4, 7, 5, 2, 6, 1, 3}, solution);
    }
}