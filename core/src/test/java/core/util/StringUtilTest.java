package core.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void longestCommonSubsequence() {
        assertEquals("AC", StringUtil.longestCommonSubsequence("ABC", "ACD").toString());
        assertEquals("GTAB", StringUtil.longestCommonSubsequence("AGGTAB", "GXTXAYB").toString());
        assertEquals("A", StringUtil.longestCommonSubsequence("ABC", "CBA").toString());
        assertEquals("XYZ", StringUtil.longestCommonSubsequence("XYZW", "XYWZ").toString());
        assertEquals("ADH", StringUtil.longestCommonSubsequence("ABCDGH", "AEDFHR").toString());
        assertEquals("AC", StringUtil.longestCommonSubsequence("ABC", "AC").toString());
        assertEquals("geeks", StringUtil.longestCommonSubsequence("geeks", "geeksfor", "geeksforgeeks").toString());
        assertEquals("b1e", StringUtil.longestCommonSubsequence("abcd1e2", "bc12ea", "bd1ea").toString());
        assertEquals("", StringUtil.longestCommonSubsequence("abcd", "efgh", "ijkl").toString());
    }
}