package oop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionThree {
    @Test
    public void testFraccion() {
        Fraction f1 = new Fraction(2, 5);
        Fraction f2 = new Fraction(4, 6);
        assertEquals("2/5", f1.toString());
        assertEquals("4/6", f2.toString());
        Fraction f3 = f1.multiplicate(f2);
        assertEquals("8/30", f3.toString());
        Fraction f4 = f2.sum(f3);
        assertEquals("168/180", f4.toString());
        Fraction f5 = f3.sub(f4);
        assertEquals("-3600/5400", f5.toString());
        assertEquals(0.4, f1.decimal(), 0);
        assertEquals(0, f1.roundToInteger());
    }
}