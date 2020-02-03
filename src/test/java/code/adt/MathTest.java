package code.adt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathTest {

    @Test
    public void sin() {
        assertEquals(0, Math.sin(0), 0.000001);
        assertEquals(0, Math.sin(java.lang.Math.PI), 0.000001);
        assertEquals(1, Math.sin(java.lang.Math.PI / 2), 0.000001);
        assertEquals(-1, Math.sin(java.lang.Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(0, Math.sin(java.lang.Math.PI + (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(1, Math.sin(java.lang.Math.PI / 2 + (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(-1, Math.sin(java.lang.Math.PI * (3.0 / 2) + (java.lang.Math.PI * 2 * 100)), 0.000001);

        assertEquals(0, Math.sin(-java.lang.Math.PI), 0.000001);
        assertEquals(-1, Math.sin(-java.lang.Math.PI / 2), 0.000001);
        assertEquals(1, Math.sin(-java.lang.Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(0, Math.sin(-java.lang.Math.PI - (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(-1, Math.sin(-java.lang.Math.PI / 2 - (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(1, Math.sin(-java.lang.Math.PI * (3.0 / 2) - (java.lang.Math.PI * 2 * 100)), 0.000001);
    }

    @Test
    public void cos() {
        assertEquals(1, Math.cos(0), 0.000001);
        assertEquals(-1, Math.cos(java.lang.Math.PI), 0.000001);
        assertEquals(0, Math.cos(java.lang.Math.PI / 2), 0.000001);
        assertEquals(0, Math.cos(java.lang.Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(-1, Math.cos(java.lang.Math.PI + (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(java.lang.Math.PI / 2 + (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(java.lang.Math.PI * (3.0 / 2) + (java.lang.Math.PI * 2 * 100)), 0.000001);

        assertEquals(-1, Math.cos(-java.lang.Math.PI), 0.000001);
        assertEquals(0, Math.cos(-java.lang.Math.PI / 2), 0.000001);
        assertEquals(0, Math.cos(-java.lang.Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(-1, Math.cos(-java.lang.Math.PI - (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(-java.lang.Math.PI / 2 - (java.lang.Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(-java.lang.Math.PI * (3.0 / 2) - (java.lang.Math.PI * 2 * 100)), 0.000001);
    }

    @Test
    public void abs() {
        assertEquals(100, Math.abs(100), 0);
        assertEquals(100, Math.abs(-100), 0);
    }

    @Test
    public void test() {
        assertEquals(6, Math.gcd(270, 192));
    }
}