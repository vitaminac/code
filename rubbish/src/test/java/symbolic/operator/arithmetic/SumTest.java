package symbolic.operator.arithmetic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import symbolic.Expression;
import symbolic.operand.Constant;
import symbolic.operand.Symbol;

import static junit.framework.TestCase.assertEquals;

public class SumTest {
    private Expression sumOfTwoMultiplication;
    private Expression fiveTimesX;

    @Before
    public void setUp() throws Exception {
        this.fiveTimesX = new Multiply(new Constant(5), new Symbol("x"));
        this.sumOfTwoMultiplication = new Sum(new Multiply(new Symbol("x"), new Constant(2)), new Multiply(new Symbol("x"), new Constant(3)));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void derivative() {
        assertEquals("(y + 2x)/dx=2", new Constant(2), new Sum(new Symbol("y"), new Multiply(new Constant(2), new Symbol("x"))).derivative(new Symbol("x")));
    }

    @Test
    public void substitute() {
        assertEquals("f(x) = 2 + x, f(5) = 7", new Sum(new Symbol("x"), new Constant(2)).substitute(new Symbol("x"), 5), new Constant(7));
    }

    @Test
    public void simplify() {
        assertEquals("2x + 3x can simplied to 5x", this.fiveTimesX, this.sumOfTwoMultiplication.simplify());
    }

    @Test
    public void canBeSimplified() {
    }

    @Test
    public void commutate() {
    }
}