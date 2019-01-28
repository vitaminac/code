package dinamicprogramming;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RodCuttingTest {
    private RodCutting p = new RodCutting(new double[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 10);
    private int[] cuts = new int[]{0, 1, 2, 3, 2, 2, 6, 1, 2, 3, 10};

    @Test
    public void bottomUpTest() {
        assertEquals(30, p.bottomUp(), 0);
        assertArrayEquals(cuts, p.getCuts());
    }

    @Test
    public void topDownTest() {
        assertEquals(30, p.topDown(), 0);
        assertArrayEquals(cuts, p.getCuts());
    }
}