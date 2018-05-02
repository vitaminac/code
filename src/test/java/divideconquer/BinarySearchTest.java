package divideconquer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinarySearchTest {
    @Test
    public void testBinarySearch() {
        BinarySearch<Integer> intSearcher = BinarySearch.create();
        Integer[] arrInt = {2, 3, 6, 8, 10, 12, 14, 16};
        assertEquals(0, intSearcher.binarySearch(arrInt, 2));
        assertEquals(-1, intSearcher.binarySearch(arrInt, 4));
        assertEquals(3, intSearcher.binarySearch(arrInt, 8));
        BinarySearch<Double> doubleSearcher = BinarySearch.create();
        Double[] arrDoubles = {2.2, 2.4, 2.6, 2.9, 3.3};
        assertEquals(0, doubleSearcher.binarySearch(arrDoubles, 2.2));
        assertEquals(4, doubleSearcher.binarySearch(arrDoubles, 3.3));
        assertEquals(-1, doubleSearcher.binarySearch(arrDoubles, 3.2));
    }
}