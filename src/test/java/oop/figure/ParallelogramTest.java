package oop.figure;

import oop.coordinates.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParallelogramTest {
    @Test
    public void testArea() {
        assertEquals(12000.0, new Parallelogram(new Vector(100, 0), new Vector(0, 120)).area(), 0.000000001);
    }

}