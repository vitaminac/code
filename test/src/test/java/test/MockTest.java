package test;

import org.junit.Test;

import java.util.List;

import static test.Asserts.assertEquals;
import static test.Asserts.assertThrows;
import static test.Mock.*;

public class MockTest {
    @Test
    public void test() {
        List<?> list = mock(List.class);
        when(list.size()).thenReturn(10);
        when(list.remove(1)).thenThrow(IndexOutOfBoundsException::new);
        verify(list).clear();
        finishPreparing(list);

        assertEquals(10, list.size(), "expected to returned 10");
        assertThrows(() -> list.remove(1), IndexOutOfBoundsException.class);
        list.clear();
        verifyExpectedInteractions(list);
    }
}