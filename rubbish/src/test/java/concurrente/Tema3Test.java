package concurrente;

import org.junit.Test;

import java.util.concurrent.Semaphore;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Tema3Test {

    @Test
    public void mat_mul() throws Exception {
        assertArrayEquals(new long[]{58, 64, 139, 154}, Tema3.mat_mul(
                new long[]{1, 2, 3, 4, 5, 6},
                new long[]{7, 8, 9, 10, 11, 12},
                2,
                3,
                2
        ));
    }

    @Test
    public void count() throws InterruptedException {
        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertEquals(str.length(), Tema3.count(str, 10));
    }

    @Test
    public void merge_sort() {
        Integer[] arr = {5, 0, 3, 4, 9, 1, 2, 6, 7, 8};
        Tema3.merge_sort(arr, new Integer[arr.length], 0, arr.length - 1, new Semaphore(4));
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }

    @Test
    public void n_cajero() throws InterruptedException {
        assertEquals(1000, Tema3.n_cajero(1000, 10));
    }
}