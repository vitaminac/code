package code.adt.queue.simulation;

import collections.hashtable.SeparateChainingHashTable;
import collections.map.MutableMap;
import collections.set.MutableSet;

import java.util.PriorityQueue;


public class Clock {
    private double time = 0;
    // private PriorityQueue<Event> events = ArrayHeap.create(); TODO
    private final PriorityQueue<Event> events = new PriorityQueue<>();

    private final MutableSet<Runnable> listeners = MutableSet.fromMap(() -> MutableMap.fromHashTable(SeparateChainingHashTable::new));

    public void run(int hours) {
        var until = hours * 60 * 60;
        while (time < until && !this.events.isEmpty()) {
            var event = this.events.peek();
            if (event.getTime() <= this.time) {
                event.doHappen();
                this.tick();
                this.events.remove();
            } else {
                this.time = event.getTime();
            }
        }
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void addListeners(Runnable runnable) {
        this.listeners.add(runnable);
    }

    public void tick() {
        for (var listener : this.listeners) {
            listener.run();
        }
    }

    public double getTime() {
        return time;
    }
}