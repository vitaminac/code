package symbolic.operator.arithmetic;

import org.junit.Before;
import org.junit.Test;
import symbolic.operand.Constant;
import symbolic.operand.Symbol;

import static org.junit.Assert.assertEquals;

public class MultiplyTest {
    private Multiply twoTimeX;
    private Multiply xTimeTwo;
    private Multiply threeTimeX;
    private Multiply twoTimeY;
    private Multiply threeTimeY;
    private Multiply twoByTwo;
    private Multiply zeroTimeX;

    @Before
    public void setUp() {
        this.twoTimeX = new Multiply(new Constant(2), new Symbol("x"));
        this.xTimeTwo = new Multiply(new Symbol("x"), new Constant(2));
        this.threeTimeX = new Multiply(new Symbol("x"), new Constant(3));
        this.twoTimeY = new Multiply(new Constant(2), new Symbol("y"));
        this.threeTimeY = new Multiply(new Constant(3), new Symbol("y"));
        this.twoByTwo = new Multiply(new Constant(2), new Constant(2));
        this.zeroTimeX = new Multiply(new Constant(0), new Symbol("x"));
    }


    @Test
    public void absorb() {
        assertEquals("x was absorbing by zero element", this.zeroTimeX.absorb(), new Constant(0));
        assertEquals("2 isn't zero element, keep untouched", this.twoTimeX.absorb(), this.twoTimeX);
        assertEquals("2 isn't zero element, keep untouched", this.twoTimeX.absorb(), this.xTimeTwo);
    }

    @Test
    public void derivative() {
        assertEquals(this.twoTimeX.derivative(new Symbol("x")), new Constant(2));
        assertEquals(this.twoTimeX.derivative(new Symbol("x")).toString(), String.valueOf(2.0));
    }

    @Test
    public void substitute() {
        assertEquals(this.twoTimeX.substitute(new Symbol("   x "), 8), new Constant(16));
        assertEquals(this.xTimeTwo.substitute(new Symbol("   x "), 8), new Constant(16));
        assertEquals(this.xTimeTwo.substitute(new Symbol("   x "), 9), new Constant(18));
    }

    @Test
    public void simplify() {
        assertEquals(this.twoByTwo.simplify(), new Constant(4));
    }

    @Test
    public void commutate() {
        assertEquals(this.twoTimeX.commutate().toString(), this.xTimeTwo.toString());
        assertEquals(this.xTimeTwo.commutate().toString(), this.twoTimeX.toString());
    }

    @Test
    public void equals() {
        assertEquals(this.twoTimeX, this.xTimeTwo);
    }

    @Test
    public void greatestCommonFactor() {
        final String sameGCF = "the Greatest common divisor of two same expression is themselves";
        assertEquals(sameGCF, new Multiply(new Constant(2), new Symbol("x")), this.twoTimeX.greatestCommonFactor(this.twoTimeX));
        assertEquals(sameGCF, this.twoTimeX.greatestCommonFactor(this.twoTimeX), this.twoTimeX);
        assertEquals(sameGCF, this.xTimeTwo.greatestCommonFactor(this.xTimeTwo), this.xTimeTwo);
        assertEquals(sameGCF, this.threeTimeX.greatestCommonFactor(this.threeTimeX), this.threeTimeX);
        assertEquals(sameGCF, this.twoTimeY.greatestCommonFactor(this.twoTimeY), this.twoTimeY);
        assertEquals(sameGCF, this.threeTimeY.greatestCommonFactor(this.threeTimeY), this.threeTimeY);

        assertEquals("the MCF is 2, x cannot be factored", new Constant(2), this.twoTimeX.greatestCommonFactor(this.twoByTwo));
        assertEquals(this.twoTimeX.greatestCommonFactor(this.twoTimeY), new Constant(2));

        assertEquals(this.twoTimeX.greatestCommonFactor(this.threeTimeX), new Symbol("x"));

        assertEquals(this.twoTimeY.greatestCommonFactor(this.threeTimeY), new Symbol("y"));
    }

    @Test
    public void toStringTest() {
        assertEquals("(2.0) * (x)", this.twoTimeX.toString());
        assertEquals("(x) * (2.0)", this.xTimeTwo.toString());
    }
}