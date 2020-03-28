package cp_algorithms.algebra;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryExponentiationTest {
    @Test
    public void bin_pow() {
        assertEquals(12, BinaryExponentiation.binpow(122, 234, 13));
    }

    @Test
    public void fibonacci() {
        assertEquals(308061521170129L, BinaryExponentiation.fibonacci(71));
    }

    @Test
    public void permutation() {
        assertEquals(11390625, BinaryExponentiation.permutation(15, 6));
    }
}