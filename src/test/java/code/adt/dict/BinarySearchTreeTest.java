package code.adt.dict;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
public class BinarySearchTreeTest {

    private BinarySearchTree<Integer, String> binarySearchTree;
    private Supplier<BinarySearchTree<Integer, String>> supplier;

    public BinarySearchTreeTest(Supplier<BinarySearchTree<Integer, String>> supplier) {
        this.supplier = supplier;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<BinarySearchTree<Integer, String>>) UnbalancedBinarySearchTree::new},
                {(Supplier<BinarySearchTree<Integer, String>>) AVLTree::new},
        });
    }


    @Before
    public void setUp() {
        this.binarySearchTree = supplier.get();
    }
}