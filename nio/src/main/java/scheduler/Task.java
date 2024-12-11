package scheduler;

public interface Task extends Job {
    void cancel();
}
