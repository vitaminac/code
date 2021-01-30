package core.tree;

import org.junit.Test;

public class RedBlackTreeTest {

    @Test
    public void testToString() {
        var tree = new RedBlackTree<Integer, String>();
        for (int i = 0; i < 10; i++) {
            tree.insert(i, String.valueOf(i));
            System.out.println(tree.toString());
        }
    }
}