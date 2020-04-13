package code.adt.queue.simulation;

public class Event implements Comparable<Event> {
    private double time;
    private Runnable runnable;

    public Event(double time, Runnable runnable) {
        this.time = time;
        this.runnable = runnable;
    }

    public void doHappen() {
        this.runnable.run();
    }

    public double getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.time, o.time);
    }
}