package leetcode;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class P224BasicCalculatorTest {
    private P224BasicCalculator solution;

    @Before
    public void setUp() {
        this.solution = new P224BasicCalculator();
    }

    @Test
    public void calculateExample1() {
        assertEquals(2, solution.calculate("1 + 1"));
    }

    @Test
    public void calculateExample2() {
        assertEquals(3, solution.calculate(" 2-1 + 2 "));
    }

    @Test
    public void calculateExample3() {
        assertEquals(23, solution.calculate("(1+(4+5+2)-3)+(6+8)"));
    }

    @Test
    public void calculateExample4() {
        assertEquals(-22, solution.calculate("1+(-23)"));
    }

    @Test
    public void calculateExample5() {
        assertEquals(-12, solution.calculate("- (3 + (4 + 5))"));
    }

    @Test
    public void calculateExample6() {
        assertEquals(3, solution.calculate(" 2-1 + 2 "));
    }
}