package scheduler;

public abstract class ScheduledTask implements Comparable<ScheduledTask>, Job {
    private long scheduleDate;

    public ScheduledTask(long scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return (this.scheduleDate - o.scheduleDate) > 0 ? 1 : -1;
    }

    public long getStartDate() {
        return this.scheduleDate;
    }
}
