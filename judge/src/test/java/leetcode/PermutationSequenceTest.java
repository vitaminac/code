package leetcode;

import org.junit.Assert;
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
        Assert.assertEquals("213", this.permutation.getPermutation(3, 3));
    }

    @Test
    public void test2() {
        Assert.assertEquals("2314", this.permutation.getPermutation(4, 9));
    }

    @Test
    public void test3() {
        Assert.assertEquals("132", this.permutation.getPermutation(3, 2));
    }
}