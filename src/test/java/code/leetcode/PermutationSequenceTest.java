package code.leetcode;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PermutationSequenceTest {
    private PermutationSequence permutation;

    @Before
    public void setUp() {
        this.permutation = new PermutationSequence();
    }

    @Test
    public void test1() {
        assertEquals("213", this.permutation.getPermutation(3, 3));
    }

    @Test
    public void test2() {
        assertEquals("2314", this.permutation.getPermutation(4, 9));
    }

    @Test
    public void test3() {
        assertEquals("132", this.permutation.getPermutation(3, 2));
    }
}