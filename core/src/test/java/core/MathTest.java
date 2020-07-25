package core;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MathTest {

    @Test
    public void sin() {
        assertEquals(0, Math.sin(0), 0.000001);
        assertEquals(0, Math.sin(Math.PI), 0.000001);
        assertEquals(1, Math.sin(Math.PI / 2), 0.000001);
        assertEquals(-1, Math.sin(Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(0, Math.sin(Math.PI + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(1, Math.sin(Math.PI / 2 + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(-1, Math.sin(Math.PI * (3.0 / 2) + (Math.PI * 2 * 100)), 0.000001);

        assertEquals(0, Math.sin(-Math.PI), 0.000001);
        assertEquals(-1, Math.sin(-Math.PI / 2), 0.000001);
        assertEquals(1, Math.sin(-Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(0, Math.sin(-Math.PI - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(-1, Math.sin(-Math.PI / 2 - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(1, Math.sin(-Math.PI * (3.0 / 2) - (Math.PI * 2 * 100)), 0.000001);
    }

    @Test
    public void cos() {
        assertEquals(1, Math.cos(0), 0.000001);
        assertEquals(-1, Math.cos(Math.PI), 0.000001);
        assertEquals(0, Math.cos(Math.PI / 2), 0.000001);
        assertEquals(0, Math.cos(Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(-1, Math.cos(Math.PI + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(Math.PI / 2 + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(Math.PI * (3.0 / 2) + (Math.PI * 2 * 100)), 0.000001);

        assertEquals(-1, Math.cos(-Math.PI), 0.000001);
        assertEquals(0, Math.cos(-Math.PI / 2), 0.000001);
        assertEquals(0, Math.cos(-Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(-1, Math.cos(-Math.PI - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(-Math.PI / 2 - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Math.cos(-Math.PI * (3.0 / 2) - (Math.PI * 2 * 100)), 0.000001);
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

    @Test
    public void dec() {
        assertEquals(0, Math.dec(1));
    }

    @Test
    public void booth_mul() {
        assertEquals(-12, Math.booth_mul(3, -4));
        assertEquals(81, Math.booth_mul(9, 9));
        assertEquals(0, Math.booth_mul(0, 0));
        assertEquals(-81, Math.booth_mul(-9, 9));
        assertEquals(-81, Math.booth_mul(9, -9));
    }

    @Test
    public void divide() {
        assertArrayEquals(new long[]{9, 5}, Math.divide(86, 9));
        assertArrayEquals(new long[]{-9, -5}, Math.divide(-86, 9));
        assertArrayEquals(new long[]{-9, 5}, Math.divide(86, -9));
    }

    @Test
    public void karatsuba_mul() {
        assertEquals(BigInteger.valueOf(123456789).multiply(BigInteger.valueOf(987654321)), Math.karatsuba_mul(BigInteger.valueOf(123456789), BigInteger.valueOf(987654321)));
    }

    @Test
    public void count_bits() {
        assertEquals(9, Math.count_bits(46139));
    }

    @Test
    public void mod3() {
        assertEquals(123456789 % 3, Math.mod3(123456789));
    }

    @Test
    public void testSum() {
        assertEquals(496, Math.sum(new int[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f
        }));
    }
}