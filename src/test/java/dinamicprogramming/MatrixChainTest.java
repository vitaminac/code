package dinamicprogramming;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixChainTest {

    @Test
    public void solve() {
        final MatrixChain matrixChain = new MatrixChain(new int[]{30, 35, 15, 5, 10, 20, 25});
        int nMultiplication = matrixChain.solve();
        System.out.println(matrixChain);
        assertEquals(15125, nMultiplication);
    }
}