package greedy.task;

import java.util.HashSet;

public class GreedyWaitTime {
    private int[] sol;
    private Task[] tasks;
    private HashSet<Integer> cand;

    public GreedyWaitTime(Task[] tasks) {
        this.tasks = tasks;
    }

    public void greedyAlgorithmWT() {
        this.sol = new int[this.tasks.length];
        int i = 0;
        while (!this.cand.isEmpty()) {
            int bestTask = this.getBestTask();
            this.cand.remove(bestTask);
            this.sol[i++] = bestTask;
        }
    }

    private int getBestTask() {
        double bestTimeTask = Double.MAX_VALUE;
        int bestTask = 0;
        for (int i : this.cand) {
            double time = this.tasks[i].getTaskTime();
            if (time < bestTimeTask) {
                bestTimeTask = time;
                bestTask = i;
            }
        }
        return bestTask;
    }

    public static void main(String[] args) {

    }
}
