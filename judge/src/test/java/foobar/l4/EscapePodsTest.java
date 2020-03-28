package foobar.l4;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EscapePodsTest {

    @Test
    public void solution() {
        Assert.assertEquals(6, EscapePods.solution(new int[]{0}, new int[]{3}, new int[][]{{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}}));
        Assert.assertEquals(16, EscapePods.solution(new int[]{0, 1}, new int[]{4, 5}, new int[][]{{0, 0, 4, 6, 0, 0}, {0, 0, 5, 2, 0, 0}, {0, 0, 0, 0, 4, 4}, {0, 0, 0, 0, 6, 6}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}));
    }
}