package core.dict;

import org.junit.Test;

import static org.junit.Assert.*;

public class RedBlackTreeTest {

    @Test
    public void testToString() {
        var tree = new RedBlackTree<Integer, String>();
        for (int i = 0; i < 10; i++) {
            tree.link(i, String.valueOf(i));
            System.out.println(tree.toString());
        }
    }
}