package code.lintcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class P212SpaceReplacementTest {

    @Test
    public void replaceBlank() {
        char[] string = "We are happy.                                  ".toCharArray();
        int len = new P212SpaceReplacement().replaceBlank(string, 13);
        assertEquals(17, len);
        assertEquals("We%20are%20happy.", String.valueOf(string, 0, len));
    }
}