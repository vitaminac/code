package core;

import core.util.Rational;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RationalTest {
    @Test
    public void test() {
        Rational f1 = new Rational(2, 5);
        Rational f2 = new Rational(4, 6);
        assertEquals("2/5", f1.toString());
        assertEquals("2/3", f2.toString());
        Rational f3 = f1.multiply(f2);
        assertEquals("4/15", f3.toString());
        Rational f4 = f2.sum(f3);
        assertEquals("14/15", f4.toString());
        Rational f5 = f3.minus(f4);
        assertEquals("-2/3", f5.toString());
        assertEquals(0.4, f1.getDoubleValue(), 0);
    }

    @Test
    public void test_whenNegative() {
        assertEquals("2/3", new Rational(-2, -3).toString());
        assertEquals("-2/3", new Rational(-2, 3).toString());
        assertEquals("-2/3", new Rational(2, -3).toString());
        assertEquals("-2/3", new Rational(-4, 6).toString());
        assertEquals("-2/3", new Rational(4,-6).toString());
        assertEquals("-3/2", new Rational(-6, 4).toString());
        assertEquals("-3/2", new Rational(6,-4).toString());
        assertEquals(new Rational(-2, 3), new Rational(2, -3));
    }

    @Test
    public void test_whenNumeratorIsZero() {
        assertEquals(new Rational(0, -6), new Rational(0, -6));
        assertEquals(new Rational(0, -6), new Rational(0, -3));
    }
}