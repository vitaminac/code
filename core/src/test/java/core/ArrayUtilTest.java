package core;

import core.util.ArrayUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayUtilTest {
    @Test
    public void binarySearch() {
        assertEquals(-1, ArrayUtil.binarySearch(new Integer[0], 0));
        assertEquals(-1, ArrayUtil.binarySearch(new Integer[0], Integer.MAX_VALUE));
        assertEquals(-1, ArrayUtil.binarySearch(new Integer[0], Integer.MIN_VALUE));
        assertEquals(0, ArrayUtil.binarySearch(java.util.Arrays.stream(new int[]{1, 13, 56, 78, 99}).boxed().toArray(Integer[]::new), 1));
        assertEquals(0, ArrayUtil.binarySearch(java.util.Arrays.stream(new int[]{-1, 13, 56, 78, 99}).boxed().toArray(Integer[]::new), -1));
        assertEquals(0, ArrayUtil.binarySearch(java.util.Arrays.stream(new int[]{0, 13, 56, 78, 99}).boxed().toArray(Integer[]::new), 0));
        assertEquals(0, ArrayUtil.binarySearch(java.util.Arrays.stream(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}).boxed().toArray(Integer[]::new), Integer.MIN_VALUE));
        assertEquals(0, ArrayUtil.binarySearch(java.util.Arrays.stream(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}).boxed().toArray(Integer[]::new), Integer.MAX_VALUE));
    }

    @Test
    public void quick_sort() {
        Integer[] arr = {5, 0, 3, 4, 9, 19, 80, 1, 2, 6, 7, 8};
        ArrayUtil.quick_sort(arr, 0, arr.length - 1);
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 19, 80}, arr);
    }

    @Test
    public void merge_sort() {
        Integer[] arr = {5, 0, 3, 4, 9, 1, 2, 6, 7, 8};
        ArrayUtil.merge_sort(arr, new Integer[arr.length], 0, arr.length - 1, Integer::compareTo);
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }
}