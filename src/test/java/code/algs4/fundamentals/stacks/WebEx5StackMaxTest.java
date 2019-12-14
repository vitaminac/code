package code.algs4.fundamentals.stacks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebEx5StackMaxTest {

    @Test
    public void test() {
        StackMax webEx5StackMax = new StackMax();
        webEx5StackMax.push(0);
        assertEquals(0, webEx5StackMax.max());
        webEx5StackMax.push(-1);
        assertEquals(0, webEx5StackMax.max());
        webEx5StackMax.push(2);
        assertEquals(2, webEx5StackMax.max());
        webEx5StackMax.push(100);
        assertEquals(100, webEx5StackMax.max());
        webEx5StackMax.push(50);
        assertEquals(100, webEx5StackMax.max());
        assertEquals(50, (int) webEx5StackMax.pop());
        assertEquals(100, webEx5StackMax.max());
        assertEquals(100, (int) webEx5StackMax.pop());
        assertEquals(2, webEx5StackMax.max());
        assertEquals(2, (int) webEx5StackMax.pop());
        assertEquals(0, webEx5StackMax.max());
        assertEquals(-1, (int) webEx5StackMax.pop());
        assertEquals(0, webEx5StackMax.max());
        assertEquals(0, (int) webEx5StackMax.pop());
    }
}