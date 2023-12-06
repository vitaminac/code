package collections.tree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedBlackTreeTest {
    @Test
    public void testSize_happyPath() {
        var tree = new RedBlackTree<Integer, String>();
        for (int i = 0; i < 10; i++) {
            tree.insert(i, String.valueOf(i));
        }
        assertEquals(10, tree.size());
    }
}