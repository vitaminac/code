package code.adt.queue;

import code.adt.queue.simulation.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Next customer is assigned to the smaller of the two queues. Use 2 FIFO
 * queues. Perception that you always pick the longer line (or wrong lane) when
 * approaching a toll plaza. Suppose two cars enter the toll plaza at the same
 * time and pick different queues of the same length. Compute average length in
 * time that one car will beat the other car by.
 */
public class kMM1Queue {
    @Test
    public void testKMM1Queue() {
        Clock clock = new Clock();
        Producer producer = new Producer(clock, 45.0 / 60 / 60);
        InfiniteQueue infiniteQueue = new InfiniteQueue(clock);
        infiniteQueue.addTriggerOnEnter((atom) -> atom.save("response_time", clock.getTime()));
        producer.addOutput(infiniteQueue);
        SingleServer server = new SingleServer(clock, 1.1 / 60);
        server.addTriggerOnEnter((atom) -> atom.save("process_time", clock.getTime()));
        server.addTriggerOnExit((atom) -> atom.save("process_time", clock.getTime() - (Double) atom.get("process_time")));
        server.addTriggerOnExit((atom) -> atom.save("response_time", clock.getTime() - (Double) atom.get("response_time")));
        infiniteQueue.addOutput(server);
        Consumer consumer = new Consumer();
        server.addOutput(consumer);
        var hours = 1000;
        clock.run(hours);

        var bag = consumer.getBag();
        double totalResponseTime = 0;
        double totalProcessTime = 0;
        for (var atom : bag) {
            totalResponseTime += (Double) atom.get("response_time");
            totalProcessTime += (Double) atom.get("process_time");
        }
        assertEquals(45, bag.size() / hours, 1);
        assertEquals(2.8, totalResponseTime / bag.size() / 60, 0.2);
        assertEquals(1 / 1.1, totalProcessTime / bag.size() / 60, 0.1);
    }
}