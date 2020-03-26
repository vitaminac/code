package code.leetcode.bfs;

import org.junit.Test;

import static org.junit.Assert.*;

public class SlidingPuzzleTest {
    private SlidingPuzzle puzzle = new SlidingPuzzle();

    @Test
    public void slidingPuzzle() {
        assertEquals(1, puzzle.slidingPuzzle(new int[][]{{1, 2, 3}, {4, 0, 5}}));
        assertEquals(-1, puzzle.slidingPuzzle(new int[][]{{1, 2, 3}, {5, 4, 0}}));
        assertEquals(5, puzzle.slidingPuzzle(new int[][]{{4, 1, 2}, {5, 0, 3}}));
        assertEquals(14, puzzle.slidingPuzzle(new int[][]{{3, 2, 4}, {1, 5, 0}}));
    }
}