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
    public void count() throws Exception {
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
    public void n_cajero() throws Exception {
        assertEquals(1000, Tema3.n_cajero(1000, 10));
    }

    @Test
    public void ask_answer() throws Exception {
        Tema3.ask_answer(10);
    }

    @Test
    public void download() throws Exception {
        Tema3.download("http://i.imgur.com/z4d4kWk.jpg", "C:\\Users\\vitam\\Downloads\\abc.jpg", 30);
    }

    @Test
    public void to_grey() throws Exception {
        Tema3.to_grey("C:\\Users\\vitam\\Downloads\\abc.jpg", 10);
    }

    @Test
    public void ges_account() throws InterruptedException {
        Tema3.ges_account(1000, 1000);
    }

    @Test
    public void barber() throws Exception {
        Tema3.barber(10, 100);
    }
}