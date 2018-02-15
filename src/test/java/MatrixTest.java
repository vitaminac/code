import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatrixTest {
    private Matrix<Integer> matrixA;
    private Matrix<Integer> matrixB;
    private Matrix<Integer> matrixC;

    @Before
    public void setUp() throws Exception {
        this.matrixA = new Matrix<>(new Integer[][]{{1, 2}, {3, 4}});
        this.matrixB = new Matrix<>(new Integer[][]{{5, 6}, {7, 8}});
        this.matrixC = new Matrix<>(new Integer[][]{{19, 22}, {43, 50}});
    }

    @Test
    public void testMultiplication() {
        // assertEquals(this.matrixC, this.matrixA.multiply(this.matrixB));
    }

    @Test
    public void testEquals() {
        assertTrue(this.matrixA.equals(this.matrixA));
        assertTrue(!this.matrixA.equals(this.matrixB));
        assertTrue(!this.matrixA.equals(this.matrixC));
        assertTrue(!this.matrixB.equals(this.matrixC));
    }
}