package code.leetcode.dc;

import org.junit.Test;

import static org.junit.Assert.*;

public class TheSkylineProblemTest {
    private TheSkylineProblem skyline = new TheSkylineProblem();

    @Test
    public void getSkyline() {
        int[][] buildings = new int[25][25];
        buildings[2][9] = 10;
        buildings[3][7] = 15;
        buildings[5][12] = 12;
        buildings[15][20] = 10;
        buildings[19][24] = 8;
        System.out.println(this.skyline.getSkyline(buildings));
    }
}