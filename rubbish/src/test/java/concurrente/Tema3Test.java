package concurrente;

import org.junit.Test;

import static org.junit.Assert.*;

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

}