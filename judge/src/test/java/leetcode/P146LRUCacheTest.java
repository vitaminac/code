package leetcode;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class P146LRUCacheTest {
    @Test
    public void test1() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        Assert.assertEquals(1, cache.get(1));
        cache.put(3, 3);
        Assert.assertEquals(-1, cache.get(2));
        cache.put(4, 4);
        Assert.assertEquals(-1, cache.get(1));
        Assert.assertEquals(3, cache.get(3));
        Assert.assertEquals(4, cache.get(4));
    }

    @Test
    public void test2() {
        LRUCache cache = new LRUCache(1);
        cache.put(2, 1);
        Assert.assertEquals(1, cache.get(2));
        cache.put(3, 2);
        Assert.assertEquals(-1, cache.get(2));
        Assert.assertEquals(2, cache.get(3));
    }
}