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
        assertEquals(sameGCF, this.twoTimeX, this.twoTimeX.greatestCommonFactor(this.twoTimeX));
        assertEquals(sameGCF, this.twoTimeX, this.twoTimeX.greatestCommonFactor(this.xTimeTwo));
        assertEquals(sameGCF, this.xTimeTwo, this.xTimeTwo.greatestCommonFactor(this.xTimeTwo));
        assertEquals(sameGCF, this.xTimeTwo, this.xTimeTwo.greatestCommonFactor(this.twoTimeX));
        assertEquals(sameGCF, this.threeTimeX, this.threeTimeX.greatestCommonFactor(this.threeTimeX));
        assertEquals(sameGCF, this.twoTimeY, this.twoTimeY.greatestCommonFactor(this.twoTimeY));
        assertEquals(sameGCF, this.threeTimeY, this.threeTimeY.greatestCommonFactor(this.threeTimeY));
        assertEquals(sameGCF, new Constant(4), this.twoByTwo.greatestCommonFactor(this.twoByTwo));
        assertEquals(sameGCF, new Constant(0), this.zeroTimeX.greatestCommonFactor(this.zeroTimeX));

        String isX = "The common factor is x";
        assertEquals(isX, new Symbol("x"), this.twoTimeX.greatestCommonFactor(this.threeTimeX));
        assertEquals(isX, new Symbol("x"), this.xTimeTwo.greatestCommonFactor(this.threeTimeX));
        assertEquals(isX, new Symbol("x"), this.threeTimeX.greatestCommonFactor(this.twoTimeX));
        assertEquals(isX, new Symbol("x"), this.threeTimeX.greatestCommonFactor(this.xTimeTwo));

        String isY = "The common factor is y";
        assertEquals(this.twoTimeY.greatestCommonFactor(this.threeTimeY), new Symbol("y"));
        assertEquals(this.threeTimeY.greatestCommonFactor(this.twoTimeY), new Symbol("y"));


        String noCommonFactor = "they have not a common factor, the result is one";
        assertEquals(noCommonFactor, new Constant(1), this.twoTimeX.greatestCommonFactor(this.threeTimeY));
        assertEquals(noCommonFactor, new Constant(1), this.xTimeTwo.greatestCommonFactor(this.threeTimeY));
        assertEquals(noCommonFactor, new Constant(1), this.threeTimeX.greatestCommonFactor(this.twoTimeY));
        assertEquals(noCommonFactor, new Constant(1), this.threeTimeX.greatestCommonFactor(this.twoByTwo));
        assertEquals(noCommonFactor, new Constant(1), this.twoTimeY.greatestCommonFactor(this.threeTimeX));
        assertEquals(noCommonFactor, new Constant(1), this.threeTimeY.greatestCommonFactor(this.twoTimeX));
        assertEquals(noCommonFactor, new Constant(1), this.threeTimeY.greatestCommonFactor(this.xTimeTwo));
        assertEquals(noCommonFactor, new Constant(1), this.threeTimeY.greatestCommonFactor(this.twoByTwo));
        assertEquals(noCommonFactor, new Constant(1), this.twoByTwo.greatestCommonFactor(this.threeTimeX));
        assertEquals(noCommonFactor, new Constant(1), this.twoByTwo.greatestCommonFactor(this.threeTimeY));


        String isTwo = "the MCF is constant two";
        assertEquals(isTwo, new Constant(2), this.twoTimeX.greatestCommonFactor(this.twoTimeY));
        assertEquals(isTwo, new Constant(2), this.xTimeTwo.greatestCommonFactor(this.twoTimeY));
        assertEquals(isTwo, new Constant(2), this.twoTimeX.greatestCommonFactor(this.twoByTwo));
        assertEquals(isTwo, new Constant(2), this.xTimeTwo.greatestCommonFactor(this.twoByTwo));
        assertEquals(isTwo, new Constant(2), this.twoTimeY.greatestCommonFactor(this.twoTimeX));
        assertEquals(isTwo, new Constant(2), this.twoTimeY.greatestCommonFactor(this.xTimeTwo));
        assertEquals(isTwo, new Constant(2), this.twoTimeY.greatestCommonFactor(this.twoByTwo));
        assertEquals(isTwo, new Constant(2), this.twoTimeX.greatestCommonFactor(this.twoTimeY));

        String isThree = "the MCF is constant three";
        assertEquals(isTwo, new Constant(3), this.threeTimeX.greatestCommonFactor(this.threeTimeY));
        assertEquals(isTwo, new Constant(3), this.threeTimeY.greatestCommonFactor(this.threeTimeX));

        assertEquals(new Symbol("x"), this.twoTimeX.greatestCommonFactor(this.zeroTimeX));
    }

    @Test
    public void toStringTest() {
        assertEquals("(2.0) * (x)", this.twoTimeX.toString());
        assertEquals("(x) * (2.0)", this.xTimeTwo.toString());
    }
}