package code.geeksforgeeks.flow;

import org.junit.Assert;
import org.junit.Test;

public class MaximumBipartiteMatchingTest {

    @Test
    public void bmp() {
        Assert.assertEquals(5,
                new MaximumBipartiteMatching().bmp(new boolean[][]{
                        {false, true, true, false, false, false},
                        {true, false, false, true, false, false},
                        {false, false, true, false, false, false},
                        {false, false, true, true, false, false},
                        {false, false, false, false, false, false},
                        {false, false, false, false, false, true}
                }));
    }
}