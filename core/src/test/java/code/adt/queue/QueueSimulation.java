package code.adt.queue;

import code.adt.List;
import code.adt.queue.simulation.*;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class QueueSimulation {
    private static final int HOURS = 100;
    private Clock clock;

    @Before
    public void setUp() throws Exception {
        this.clock = new Clock();
    }

    @Test
    public void mm1Queue() {
        Producer producer = new Producer(this.clock, 1);
        InfiniteQueue infiniteQueue = new InfiniteQueue(clock);
        infiniteQueue.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime()));
        producer.addOutput(infiniteQueue);
        Server server = new Server(this.clock, 2);
        server.addTriggerOnEnter((atom) -> atom.save("process_time", this.clock.getTime()));
        server.addTriggerOnExit((atom) -> atom.save("process_time", this.clock.getTime() - (Double) atom.get("process_time")));
        server.addTriggerOnExit((atom) -> atom.save("response_time", this.clock.getTime() - (Double) atom.get("response_time")));
        infiniteQueue.addOutput(server);
        Consumer consumer = new Consumer();
        server.addOutput(consumer);
        this.clock.run(HOURS);

        assertEquals(1, (double) consumer.getBag().size() / HOURS / 60 / 60, 0.1);
        assertEquals(1, consumer.mean("response_time"), 0.1);
        assertEquals(1.0 / 2, consumer.mean("process_time"), 0.01);
    }

    /**
     * Compare k independent M/M/1 queues vs. M/M/k queue.
     */
    @Test
    public void mmcQueue() {
        Producer producer = new Producer(this.clock, 1);
        InfiniteQueue infiniteQueue = new InfiniteQueue(clock);
        infiniteQueue.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime()));
        infiniteQueue.addTriggerOnExit((atom) -> atom.save("process_time", this.clock.getTime()));
        producer.addOutput(infiniteQueue);

        Consumer consumer = new Consumer();
        consumer.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime() - (Double) atom.get("response_time")));
        consumer.addTriggerOnEnter((atom) -> atom.save("process_time", this.clock.getTime() - (Double) atom.get("process_time")));

        Server server1 = new Server(this.clock, 1);
        infiniteQueue.addOutput(server1);
        server1.addOutput(consumer);

        Server server2 = new Server(this.clock, 1);
        infiniteQueue.addOutput(server2);
        server2.addOutput(consumer);

        this.clock.run(HOURS);

        assertEquals(1.33333333, consumer.mean("response_time"), 0.01);
        assertEquals(1, consumer.mean("process_time"), 0.1);
    }

    /**
     * Next customer is assigned to the smaller of the two queues. Use 2 FIFO
     * queues. Perception that you always pick the longer line (or wrong lane) when
     * approaching a toll plaza. Suppose two cars enter the toll plaza at the same
     * time and pick different queues of the same length. Compute average length in
     * time that one car will beat the other car by.
     */
    @Test
    public void mm1Queue2() {
        Producer producer = new Producer(this.clock, 1, new ChooseOut() {
            @Override
            public Component choose(List<Component> components) {
                int size = Integer.MAX_VALUE;
                InfiniteQueue best = null;
                for (var component : components) {
                    InfiniteQueue queue = (InfiniteQueue) component;
                    if (queue.getSize() < size) {
                        best = queue;
                        size = queue.getSize();
                    }
                }
                return best;
            }
        });
        producer.addTriggerOnExit((atom) -> atom.save("response_time", this.clock.getTime()));
        InfiniteQueue infiniteQueue1 = new InfiniteQueue(this.clock);
        InfiniteQueue infiniteQueue2 = new InfiniteQueue(this.clock);
        producer.addOutput(infiniteQueue1);
        producer.addOutput(infiniteQueue2);
        Server server1 = new Server(this.clock, 1);
        infiniteQueue1.addOutput(server1);
        Server server2 = new Server(this.clock, 1);
        infiniteQueue2.addOutput(server2);
        Consumer consumer = new Consumer();
        consumer.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime() - (Double) atom.get("response_time")));
        server1.addOutput(consumer);
        server2.addOutput(consumer);
        clock.run(HOURS);
        System.out.println(consumer.mean("response_time"));
    }

    /**
     * Analyze queueing model with a different service distribution (G = general)
     */
    @Test
    public void mg1Queue() {
        Producer producer = new Producer(this.clock, 0.25);
        InfiniteQueue infiniteQueue = new InfiniteQueue(clock);
        infiniteQueue.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime()));
        infiniteQueue.addTriggerOnEnter((atom) -> atom.save("wait_time", this.clock.getTime()));
        infiniteQueue.addTriggerOnExit((atom) -> atom.save("wait_time", this.clock.getTime() - (Double) atom.get("wait_time")));
        producer.addOutput(infiniteQueue);
        Server server = new Server(this.clock, new GammaDistribution(3, 1), AnyAvailableChoose.instance);
        server.addTriggerOnEnter((atom) -> atom.save("process_time", this.clock.getTime()));
        server.addTriggerOnExit((atom) -> atom.save("process_time", this.clock.getTime() - (Double) atom.get("process_time")));
        server.addTriggerOnExit((atom) -> atom.save("response_time", this.clock.getTime() - (Double) atom.get("response_time")));
        infiniteQueue.addOutput(server);
        Consumer consumer = new Consumer();
        server.addOutput(consumer);
        this.clock.run(HOURS);

        assertEquals(0.25, (double) consumer.getBag().size() / HOURS / 60 / 60, 0.1);
        assertEquals(3, consumer.mean("process_time"), 0.01);
        assertEquals(6, consumer.mean("wait_time"), 0.3);
        assertEquals(9, consumer.mean("response_time"), 0.3);
    }
}