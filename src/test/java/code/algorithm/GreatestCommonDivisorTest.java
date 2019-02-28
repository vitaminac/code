package code.algorithm;

import org.junit.Test;

import static code.algorithm.GreatestCommonDivisor.gcd;
import static org.junit.Assert.assertEquals;

public class GreatestCommonDivisorTest {
    @Test
    public void test() {
        assertEquals(6, gcd(270, 192));
    }
}