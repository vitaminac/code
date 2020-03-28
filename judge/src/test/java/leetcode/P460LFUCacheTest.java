package leetcode;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class P460LFUCacheTest {
    @Test
    public void test1() {
        var cache = new LFUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        Assert.assertEquals(1, cache.get(1));
        cache.put(3, 3);
        Assert.assertEquals(-1, cache.get(2));
        Assert.assertEquals(3, cache.get(3));
        cache.put(4, 4);
        Assert.assertEquals(-1, cache.get(1));
        Assert.assertEquals(3, cache.get(3));
        Assert.assertEquals(4, cache.get(4));
    }

    @Test
    public void test2() {
        var cache = new LFUCache(3);
        cache.put(2, 2);
        cache.put(1, 1);
        Assert.assertEquals(2, cache.get(2));
        Assert.assertEquals(1, cache.get(1));
        Assert.assertEquals(2, cache.get(2));
        cache.put(3, 3);
        cache.put(4, 4);
        Assert.assertEquals(-1, cache.get(3));
        Assert.assertEquals(2, cache.get(2));
        Assert.assertEquals(1, cache.get(1));
        Assert.assertEquals(4, cache.get(4));
    }

    @Test
    public void test3() {
        var cache = new LFUCache(0);
        cache.put(0, 0);
        Assert.assertEquals(-1, cache.get(0));
    }

    @Test
    public void test4() {
        var cache = new LFUCache(10);
        cache.put(10, 13);
        cache.put(3, 17);
        cache.put(6, 11);
        cache.put(10, 5);
        cache.put(9, 10);
        Assert.assertEquals(-1, cache.get(13));
        cache.put(2, 19);
        Assert.assertEquals(19, cache.get(2));
        Assert.assertEquals(17, cache.get(3));
        cache.put(5, 25);
        Assert.assertEquals(-1, cache.get(8));
        cache.put(9, 22);
        cache.put(5, 5);
        cache.put(1, 30);
        Assert.assertEquals(-1, cache.get(11));
        cache.put(9, 12);
        Assert.assertEquals(-1, cache.get(7));
        Assert.assertEquals(5, cache.get(5));
        Assert.assertEquals(-1, cache.get(8));
        Assert.assertEquals(12, cache.get(9));
        cache.put(4, 30);
        cache.put(9, 3);
        Assert.assertEquals(3, cache.get(9));
        Assert.assertEquals(5, cache.get(10));
        Assert.assertEquals(5, cache.get(10));
        cache.put(6, 14);
        cache.put(3, 1);
        Assert.assertEquals(1, cache.get(3));
        cache.put(10, 11);
        Assert.assertEquals(-1, cache.get(8));
        cache.put(2, 14);
        Assert.assertEquals(30, cache.get(1));
        Assert.assertEquals(5, cache.get(5));
        Assert.assertEquals(30, cache.get(4));
        cache.put(11, 4);
        cache.put(12, 24);
        cache.put(5, 18);
        Assert.assertEquals(-1, cache.get(13));
        cache.put(7, 23);
        Assert.assertEquals(-1, cache.get(8));
        Assert.assertEquals(24, cache.get(12));
        cache.put(3, 27);
        cache.put(2, 12);
        Assert.assertEquals(18, cache.get(5));
        cache.put(2, 9);
        cache.put(13, 4);
        cache.put(8, 18);
        cache.put(1, 7);
        Assert.assertEquals(14, cache.get(6));
        cache.put(9, 29);
        cache.put(8, 21);
        Assert.assertEquals(18, cache.get(5));
        cache.put(6, 30);
        cache.put(1, 12);
        Assert.assertEquals(11, cache.get(10));
    }
}