package divideconquer;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

public class SkyLining {
    private final int n;
    private final int[] startPosition;
    private final int[] endPosition;
    private final int[] heights;

    public SkyLining(int n) {
        this.n = n;
        this.startPosition = new int[n];
        this.endPosition = new int[n];
        this.heights = new int[n];
    }

    public void addBuilding(int n, int left, int right, int height) {
        this.startPosition[n] = left;
        this.endPosition[n] = right;
        this.heights[n] = height;
    }

    private Queue<Pair<Integer, Integer>> plan(int left, int right) {
        Queue<Pair<Integer, Integer>> plan = new LinkedList<>();
        int mid = (left + right) / 2;
        if (mid <= left) {
            plan.add(new Pair<>(this.startPosition[left], this.heights[left]));
            plan.add(new Pair<>(this.endPosition[left], 0));
        } else {
            final Queue<Pair<Integer, Integer>> plan1 = this.plan(left, mid);
            final Queue<Pair<Integer, Integer>> plan2 = this.plan(mid, right);
            Pair<Integer, Integer> skyline1 = plan1.remove();
            Pair<Integer, Integer> skyline2 = plan2.remove();
            int height1 = 0;
            int height2 = 0;
            int lastHeight = 0;
            while ((skyline1 != null) || (skyline2 != null)) {
                Pair<Integer, Integer> nextSkyline;
                if (skyline1 == null) {
                    nextSkyline = skyline2;
                    height2 = skyline2.getValue();
                    if (!plan2.isEmpty()) {
                        skyline2 = plan2.remove();
                    } else {
                        skyline2 = null;
                    }
                } else if (skyline2 == null) {
                    nextSkyline = skyline1;
                    height1 = skyline1.getValue();
                    if (!plan1.isEmpty()) {
                        skyline1 = plan1.remove();
                    } else {
                        skyline1 = null;
                    }
                } else if (skyline1.getKey() < skyline2.getKey()) {
                    nextSkyline = skyline1;
                    height1 = skyline1.getValue();
                    if (!plan1.isEmpty()) {
                        skyline1 = plan1.remove();
                    } else {
                        skyline1 = null;
                    }
                } else {
                    nextSkyline = skyline2;
                    height2 = skyline2.getValue();
                    if (!plan2.isEmpty()) {
                        skyline2 = plan2.remove();
                    } else {
                        skyline2 = null;
                    }
                }
                int newHeight = Math.max(height1, height2);
                if (newHeight != lastHeight) {
                    plan.add(new Pair<>(nextSkyline.getKey(), newHeight));
                    lastHeight = newHeight;
                }
            }
        }
        return plan;
    }

    public Queue<Pair<Integer, Integer>> plan() {
        return this.plan(0, this.n);
    }
}
