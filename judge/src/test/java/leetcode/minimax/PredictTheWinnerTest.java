package leetcode.minimax;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredictTheWinnerTest {
    private PredictTheWinner predict = new PredictTheWinner();

    @Test
    public void predictTheWinner() {
        assertFalse(predict.PredictTheWinner(new int[]{1, 5, 2}));
        assertTrue(predict.PredictTheWinner(new int[]{1, 5, 233, 7}));
        assertTrue(predict.PredictTheWinner(new int[]{1, 1}));
    }
}