package code.algs4;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackMaxTest {

    @Test
    public void test() {
        StackMax stackMax = new StackMax();
        stackMax.push(0);
        assertEquals(0, stackMax.max());
        stackMax.push(-1);
        assertEquals(0, stackMax.max());
        stackMax.push(2);
        assertEquals(2, stackMax.max());
        stackMax.push(100);
        assertEquals(100, stackMax.max());
        stackMax.push(50);
        assertEquals(100, stackMax.max());
        assertEquals(50, (int) stackMax.pop());
        assertEquals(100, stackMax.max());
        assertEquals(100, (int) stackMax.pop());
        assertEquals(2, stackMax.max());
        assertEquals(2, (int) stackMax.pop());
        assertEquals(0, stackMax.max());
        assertEquals(-1, (int) stackMax.pop());
        assertEquals(0, stackMax.max());
        assertEquals(0, (int) stackMax.pop());
    }
}