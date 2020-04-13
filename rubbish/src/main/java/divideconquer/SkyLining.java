package divideconquer;

import java.util.LinkedList;
import java.util.Queue;

import core.map.Relation;

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

    private Queue<Relation<Integer, Integer>> plan(int left, int right) {
        Queue<Relation<Integer, Integer>> plan = new LinkedList<>();
        int mid = (left + right) / 2;
        if (mid <= left) {
            plan.add(new Relation<>(this.startPosition[left], this.heights[left]));
            plan.add(new Relation<>(this.endPosition[left], 0));
        } else {
            final Queue<Relation<Integer, Integer>> plan1 = this.plan(left, mid);
            final Queue<Relation<Integer, Integer>> plan2 = this.plan(mid, right);
            Relation<Integer, Integer> skyline1 = plan1.remove();
            Relation<Integer, Integer> skyline2 = plan2.remove();
            int height1 = 0;
            int height2 = 0;
            int lastHeight = 0;
            while ((skyline1 != null) || (skyline2 != null)) {
                Relation<Integer, Integer> nextSkyline;
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
                    plan.add(new Relation<>(nextSkyline.getKey(), newHeight));
                    lastHeight = newHeight;
                }
            }
        }
        return plan;
    }

    public Queue<Relation<Integer, Integer>> plan() {
        return this.plan(0, this.n);
    }
}
