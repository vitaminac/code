package greedy.task;

import java.util.PriorityQueue;

public class GreedyWaitTime {
    private int[] sol;
    private PriorityQueue<Candidate> candidates = new PriorityQueue<>();
    private double totalWaitTime = 0;
    private double lastWaitTime = 0;

    public GreedyWaitTime(Task[] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            this.candidates.add(new Candidate(i, tasks[i]));
        }
    }

    public void arrange() {
        this.sol = new int[this.candidates.size()];
        int i = 0;
        while (!this.candidates.isEmpty()) {
            Candidate bestCandidate = this.getBestTask();
            this.lastWaitTime += bestCandidate.task.getTaskTime();
            this.totalWaitTime += this.lastWaitTime;
            this.sol[i++] = bestCandidate.i;
        }
    }

    public double getTotalWaitTime() {
        return totalWaitTime;
    }

    /**
     * @return the best task with min wait time and remove it from candidates
     */
    private Candidate getBestTask() {
        return this.candidates.remove();
    }

    public static void main(String[] args) {
        final GreedyWaitTime greedyWaitTime = new GreedyWaitTime(new Task[]{new Task(5), new Task(10), new Task(3)});
        greedyWaitTime.arrange();
        System.out.println(greedyWaitTime.getTotalWaitTime());
    }

    private class Candidate implements Comparable<Candidate> {
        private int i;
        private Task task;

        public Candidate(int i, Task task) {
            this.i = i;
            this.task = task;
        }

        @Override
        public int compareTo(Candidate o) {
            return this.task.compareTo(o.task);
        }
    }
}
