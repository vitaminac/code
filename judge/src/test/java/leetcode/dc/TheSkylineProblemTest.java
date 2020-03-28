package leetcode.dc;

import org.junit.Test;

import static org.junit.Assert.*;

public class TheSkylineProblemTest {
    private TheSkylineProblem skyline = new TheSkylineProblem();

    @Test
    public void getSkyline() {
        int[][] buildings = new int[][]{
                {2, 9, 10},
                {3, 7, 15},
                {5, 12, 12},
                {15, 20, 10},
                {19, 24, 8}
        };
        // TODO: deep equals
        System.out.println(this.skyline.getSkyline(buildings));
    }
}