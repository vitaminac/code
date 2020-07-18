package concurrente;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Tema5Test {

    @Test
    public void experiment_invokeAll_invokeAny() throws ExecutionException, InterruptedException {
        Tema5.experiment_invokeAll_invokeAny();
    }

    @Test
    public void performance_experiment() {
        Tema5.performance_experiment(100);
    }

    @Test
    public void count() throws InterruptedException {
        assertEquals(25, Tema5.count("aaaaa eeeee iiiii ooooo uuuuu"));
    }

    @Test
    public void availability() throws Exception {
        Tema5.availability(
                Arrays.asList(
                        "https://stackoverflow.com/",
                        "http://localhost:4000",
                        "https://www.aulavirtual.urjc.es/",
                        "https://vitaminac.github.io/",
                        "https://www.google.com/",
                        "https://www.baidu.com/",
                        "http://localhost:4001"
                ).stream().map(spec -> {
                    try {
                        return new URL(spec);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList()));
    }

    @Test
    public void fjmax() {
        int[] arr = new int[]{-1, 5, 3, 8, 9, 45, 98, -85, 22, -3987, 8462, 56, 5, 444, 1, 12, 2, 3};
        assertEquals(8462, Tema5.fjmax(arr));
    }

    @Test
    public void mean() {
        assertEquals(Double.valueOf(15.0), Tema5.mean("1 2 3 4 5 6\n" + "3 4 5 6 7 8 9\n" + "10 11 12 13 14 15 16 17 18 19 20"));
    }

    @Test
    public void fjCountRange() {
        assertEquals(29, Tema5.fjCountRange(new int[]{
                54, 12, 36, 0, 2, 16, 34, 89, -56,
                -8, 3, 7, 95, 10, 56, 498, 3, 2, 1,
                56, 4789, 654, 321, 465, 789, 3120798,
                53, 45, 13, 789, 46, 12, 4, 6, 5, 9, 789,
                13, 2, 165, 1
        }, 0, 100));
    }

    @Test
    public void fjSum() {
        assertEquals(496, Tema5.fjSum(new int[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f
        }));
    }
}