package divideconquer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KthSearcherTest {

    @Test
    public void search() {
        KthSearcher<Integer> intSearcher = new KthSearcher<>();
        List<Integer> intArray = Arrays.asList(3, 5, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1);
        assertEquals(2, intSearcher.median(intArray).intValue());
        intArray = Arrays.asList(7, 2, 3, 1, 4, 6, 9, 8, 5);
        assertEquals(5, intSearcher.median(intArray).intValue());
        intArray = Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9);
        assertEquals(1, intSearcher.median(intArray).intValue());
        intArray = Arrays.asList(1, 1, 1, 1, 9, 9, 9, 9);
        assertEquals(1, intSearcher.median(intArray).intValue());
        intArray = Arrays.asList(1, 1, 1, 1, 1, 9, 9, 9, 9);
        assertEquals(1, intSearcher.median(intArray).intValue());
        intArray = Arrays.asList(1, 1, 1, 1, 9, 9, 9, 9, 9);
        assertEquals(9, intSearcher.median(intArray).intValue());
        intArray = Arrays.asList(11, 34, 22, 95, 68, 71, 50, 45, 93, 28, 64, 59, 71, 23, 64, 89, 65, 45, 73, 28, 69, 84, 73, 68, 29, 38, 16, 97);
        System.out.println(intArray.size());
        assertEquals(64, intSearcher.median(intArray).intValue());
    }
}