package foobar.l5;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DodgeTheLasersTest {

    @Test
    public void solution() {
        Assert.assertEquals("4208", Solution.solution("77"));
        Assert.assertEquals("19", Solution.solution("5"));
    }
}