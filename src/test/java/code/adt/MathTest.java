package code.adt;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
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
    public void gcd() {
        assertEquals(6, Math.gcd(270, 192));
    }

    @Test
    public void sum() {
        assertEquals(0, Math.sum(0, 0));
        assertEquals(1, Math.sum(1, 0));
        assertEquals(1, Math.sum(0, 1));
        assertEquals(2, Math.sum(1, 1));
        assertEquals(0, Math.sum(1, -1));
        assertEquals(0, Math.sum(-1, 1));
        assertEquals(-1, Math.sum(-1, 0));
        assertEquals(-1, Math.sum(0, -1));
        assertEquals(-2, Math.sum(-1, -1));
        assertEquals(-1, Math.sum(Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(-1, Math.sum(Integer.MAX_VALUE, Integer.MIN_VALUE));
    }

    @Test
    public void sub() {
        assertEquals(0, Math.sub(0, 0));
        assertEquals(1, Math.sub(1, 0));
        assertEquals(-1, Math.sub(0, 1));
        assertEquals(0, Math.sub(1, 1));
        assertEquals(2, Math.sub(1, -1));
        assertEquals(-2, Math.sub(-1, 1));
        assertEquals(-1, Math.sub(-1, 0));
        assertEquals(1, Math.sub(0, -1));
        assertEquals(0, Math.sub(-1, -1));
        assertEquals(0, Math.sub(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(0, Math.sub(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }

    @Test
    public void extended_gcd() {
        assertArrayEquals(new int[]{-9, 47}, Math.extended_gcd(240, 46));
    }

    @Test
    public void neg() {
        assertEquals(0, Math.neg(0));
        assertEquals(-1, Math.neg(1));
        assertEquals(1, Math.neg(-1));
    }

    @Test
    public void mul() {
        assertEquals(81, Math.mul(9, 9));
        assertEquals(-81, Math.mul(9, -9));
        assertEquals(-81, Math.mul(-9, 9));
        assertEquals(81, Math.mul(-9, -9));
    }
}