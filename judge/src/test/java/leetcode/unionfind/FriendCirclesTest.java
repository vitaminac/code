package leetcode.unionfind;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FriendCirclesTest {

    @Test
    public void findCircleNum() {
        Assert.assertEquals(2, new FriendCircles().findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
    }
}