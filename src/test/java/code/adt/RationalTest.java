package code.adt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RationalTest {
    @Test
    public void test() {
        Rational f1 = new Rational(2, 5);
        Rational f2 = new Rational(4, 6);
        assertEquals("2/5", f1.toString());
        assertEquals("4/6", f2.toString());
        Rational f3 = f1.multiply(f2);
        assertEquals("8/30", f3.toString());
        Rational f4 = f2.sum(f3);
        assertEquals("168/180", f4.toString());
        Rational f5 = f3.minus(f4);
        assertEquals("-3600/5400", f5.toString());
        assertEquals(0.4, f1.real(), 0);
    }
}