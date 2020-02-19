package code.geekforgeek.flow;

import org.junit.Test;

import static org.junit.Assert.*;

public class MaximumBipartiteMatchingTest {

    @Test
    public void bmp() {
        assertEquals(5,
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