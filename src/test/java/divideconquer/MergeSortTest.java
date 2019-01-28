package divideconquer;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MergeSortTest {
    @Test
    public void sort() {
        Integer[] arr = {5, 0, 3, 4, 9, 1, 2, 6, 7, 8};
        final MergeSort<Integer> sorter = MergeSort.create();
        sorter.sort(arr);
        for (int i = 0; i < arr.length - 1; i++) {
            int dff = arr[i].compareTo(arr[i + 1]);
            assertTrue(dff < 0);
        }
    }
}