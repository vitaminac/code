package greedy.task;

public class Task implements Comparable<Task> {
    private double time;

    public Task(double time) {
        this.time = time;
    }

    public double getTaskTime() {
        return this.time;
    }

    @Override
    public int compareTo(Task o) {
        return this.time - o.time < 0 ? -1 : 1;
    }
}