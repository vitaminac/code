package code.geekforgeek.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class FloyWarshallTest {
    private static int INF = Integer.MAX_VALUE / 4;

    @Test
    public void test() {
        assertArrayEquals(new int[][]{
                        {0, 5, 8, 9},
                        {INF, 0, 3, 4},
                        {INF, INF, 0, 1},
                        {INF, INF, INF, 0}
                },
                new FloyWarshall().floydWarshall(new int[][]{
                        {0, 5, INF, 10},
                        {INF, 0, 3, INF},
                        {INF, INF, 0, 1},
                        {INF, INF, INF, 0}
                }));
    }
}