package oop;

import oop.coordinates.Matrix;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MatrixTest {
    private Matrix matrixA;
    private Matrix matrixB;
    private Matrix matrixC;

    @Before
    public void setUp() throws Exception {
        this.matrixA = new Matrix(new double[][]{{1, 2}, {3, 4}});
        this.matrixB = new Matrix(new double[][]{{5, 6}, {7, 8}});
        this.matrixC = new Matrix(new double[][]{{19, 22}, {43, 50}});
    }

    @Test
    public void testMultiplication() {
        assertEquals(this.matrixC, this.matrixA.multiply(this.matrixB));
    }

    @Test
    public void testEquals() {
        assertEquals(this.matrixA, this.matrixA);
        assertNotEquals(this.matrixA, this.matrixB);
        assertNotEquals(this.matrixA, this.matrixC);
        assertNotEquals(this.matrixB, this.matrixC);
    }
}