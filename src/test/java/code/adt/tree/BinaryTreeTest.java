package code.adt.tree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class BinaryTreeTest {
    private BinaryTree<String> bst;
    private Supplier<BinaryTree<String>> supplier;

    public BinaryTreeTest(Supplier<BinaryTree<String>> supplier) {
        this.supplier = supplier;
    }

    @Before
    public void setUp() {
        this.bst = this.supplier.get();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<ArrayBinaryTree<String>>) ArrayBinaryTree::new},
                {(Supplier<LinkedBinaryTree<String>>) LinkedBinaryTree::new}
        });
    }

    @Test
    public void test() {
        this.bst.root("root");
        assertEquals("root", this.bst.root().getElement());
        assertNull(this.bst.left(this.bst.root()));
        assertNull(this.bst.right(this.bst.root()));
        this.bst.left(this.bst.root(), "left");
        this.bst.right(this.bst.root(), "right");
        assertEquals("left", this.bst.left(this.bst.root()).getElement());
        assertEquals("right", this.bst.right(this.bst.root()).getElement());
    }
}