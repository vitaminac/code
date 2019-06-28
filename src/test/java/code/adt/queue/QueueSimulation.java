package code.adt.queue;

import code.adt.queue.simulation.*;
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
        SingleServer server = new SingleServer(this.clock, 2);
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

    @Test
    public void MMCQueue() {
        Producer producer = new Producer(this.clock, 1);
        InfiniteQueue infiniteQueue = new InfiniteQueue(clock);
        infiniteQueue.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime()));
        infiniteQueue.addTriggerOnExit((atom) -> atom.save("process_time", this.clock.getTime()));
        producer.addOutput(infiniteQueue);

        Consumer consumer = new Consumer();
        consumer.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime() - (Double) atom.get("response_time")));
        consumer.addTriggerOnEnter((atom) -> atom.save("process_time", this.clock.getTime() - (Double) atom.get("process_time")));

        SingleServer server1 = new SingleServer(this.clock, 1);
        infiniteQueue.addOutput(server1);
        server1.addOutput(consumer);

        SingleServer server2 = new SingleServer(this.clock, 1);
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
    public void MM1Queue2() {
        Producer producer = new Producer(this.clock, 2/*, new ChooseOut() {
            @Override
            public Component choose(List<Component> components) {
                int size = Integer.MAX_VALUE;
                InfiniteQueue best = null;
                for (var component : components) {
                    InfiniteQueue queue = (InfiniteQueue) component;
                    if (queue.getSize() < size) {
                        best = queue;
                    }
                }
                return best;
            }
        }*/);
        producer.addTriggerOnExit((atom) -> atom.save("response_time", this.clock.getTime()));
        InfiniteQueue infiniteQueue1 = new InfiniteQueue(this.clock);
        InfiniteQueue infiniteQueue2 = new InfiniteQueue(this.clock);
        producer.addOutput(infiniteQueue1);
        producer.addOutput(infiniteQueue2);
        SingleServer server1 = new SingleServer(this.clock, 2);
        infiniteQueue1.addOutput(server1);
        SingleServer server2 = new SingleServer(this.clock, 2);
        infiniteQueue2.addOutput(server2);
        Consumer consumer = new Consumer();
        consumer.addTriggerOnEnter((atom) -> atom.save("response_time", this.clock.getTime() - (Double) atom.get("response_time")));
        server1.addOutput(consumer);
        server2.addOutput(consumer);
        clock.run(HOURS);
        System.out.println(consumer.mean("response_time"));
    }
}