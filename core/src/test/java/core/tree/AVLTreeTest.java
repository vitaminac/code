package core.tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AVLTreeTest {
    private AVLTree<Integer, Integer> tree;

    @Before
    public void setUp() throws Exception {
        this.tree = new AVLTree<>();
    }

    @Test
    public void leftRotationOnRoot() {
        this.tree.insert(1, 0);
        this.tree.insert(2, 0);
        this.tree.insert(3, 0);
        assertEquals(2, this.tree.getHeight());
    }

    @Test
    public void leftRotation() {
        this.tree.insert(2, 0);
        this.tree.insert(1, 0);
        this.tree.insert(3, 0);
        this.tree.insert(4, 0);
        this.tree.insert(5, 0);
        assertEquals(3, this.tree.getHeight());
    }

    @Test
    public void rightRotationOnRoot() {
        this.tree.insert(1, 0);
        this.tree.insert(2, 0);
        this.tree.insert(3, 0);
        assertEquals(2, this.tree.getHeight());
    }

    @Test
    public void doubleRotationLeft() {
        this.tree.insert(2, 0);
        this.tree.insert(1, 0);
        this.tree.insert(3, 0);
        this.tree.insert(5, 0);
        this.tree.insert(4, 0);
        assertEquals(3, this.tree.getHeight());
    }

    @Test
    public void doubleRotationRight() {
        this.tree.insert(6, 0);
        this.tree.insert(4, 0);
        this.tree.insert(7, 0);
        this.tree.insert(2, 0);
        this.tree.insert(3, 0);
        assertEquals(3, this.tree.getHeight());
    }
}