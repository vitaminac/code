package code.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathsTest {

    @Test
    public void sin() {
        assertEquals(0, Maths.sin(0), 0.000001);
        assertEquals(0, Maths.sin(Math.PI), 0.000001);
        assertEquals(1, Maths.sin(Math.PI / 2), 0.000001);
        assertEquals(-1, Maths.sin(Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(0, Maths.sin(Math.PI + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(1, Maths.sin(Math.PI / 2 + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(-1, Maths.sin(Math.PI * (3.0 / 2) + (Math.PI * 2 * 100)), 0.000001);

        assertEquals(0, Maths.sin(-Math.PI), 0.000001);
        assertEquals(-1, Maths.sin(-Math.PI / 2), 0.000001);
        assertEquals(1, Maths.sin(-Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(0, Maths.sin(-Math.PI - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(-1, Maths.sin(-Math.PI / 2 - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(1, Maths.sin(-Math.PI * (3.0 / 2) - (Math.PI * 2 * 100)), 0.000001);
    }

    @Test
    public void cos() {
        assertEquals(1, Maths.cos(0), 0.000001);
        assertEquals(-1, Maths.cos(Math.PI), 0.000001);
        assertEquals(0, Maths.cos(Math.PI / 2), 0.000001);
        assertEquals(0, Maths.cos(Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(-1, Maths.cos(Math.PI + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Maths.cos(Math.PI / 2 + (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Maths.cos(Math.PI * (3.0 / 2) + (Math.PI * 2 * 100)), 0.000001);

        assertEquals(-1, Maths.cos(-Math.PI), 0.000001);
        assertEquals(0, Maths.cos(-Math.PI / 2), 0.000001);
        assertEquals(0, Maths.cos(-Math.PI * (3.0 / 2)), 0.000001);

        assertEquals(-1, Maths.cos(-Math.PI - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Maths.cos(-Math.PI / 2 - (Math.PI * 2 * 100)), 0.000001);
        assertEquals(0, Maths.cos(-Math.PI * (3.0 / 2) - (Math.PI * 2 * 100)), 0.000001);
    }

    @Test
    public void abs() {
        assertEquals(100, Maths.abs(100), 0);
        assertEquals(100, Maths.abs(-100), 0);
    }
}