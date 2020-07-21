package foobar.l5;

import org.junit.Assert;
import org.junit.Test;

public class DodgeTheLasersTest {

    @Test
    public void solution() {
        Assert.assertEquals("4208", DodgeTheLasers.solution("77"));
        Assert.assertEquals("19", DodgeTheLasers.solution("5"));
    }
}