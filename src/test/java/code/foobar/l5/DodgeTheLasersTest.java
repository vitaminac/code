package code.foobar.l5;

import org.junit.Test;

import static org.junit.Assert.*;

public class DodgeTheLasersTest {

    @Test
    public void solution() {
        assertEquals("4208", Solution.solution("77"));
        assertEquals("19", Solution.solution("5"));
    }
}