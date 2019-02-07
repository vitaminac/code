package code.so;

// https://www.geeksforgeeks.org/petersons-algorithm-using-processes-shared-memory/

import java.util.Scanner;

/**
 * The producer consumer problem (or bounded buffer problem) describes two processes,
 * the producer and the consumer, which share a common, fixed-size buffer used as a queue.
 * Producer produce an item and put it into buffer.
 * <p>
 * If buffer is already full then producer will have to wait for an empty block in buffer.
 * Consumer consume an item from buffer.
 * If buffer is already empty then consumer will have to wait for an item in buffer.
 * <p>
 * Implement Peterson’s Algorithm for the two processes using shared memory
 * such that there is mutual exclusion between them.
 * The solution should have free from synchronization problems.
 * <p>
 * Input:
 * size of buffer
 * number of products
 */
public class PetersonProducerConsumer {
    private static final int CONSUMER_ID = 0;
    private static final int PRODUCER_ID = 1;

    private static volatile int[] buffer;
    private static volatile boolean[] flag = new boolean[2];
    private static volatile int turn;
    private static volatile int timestamp;
    private static int head = 0;
    private static int size = 0;

    private static void lock(int id) {
        flag[id] = true;
        turn = id;

        while (flag[1 - id] && turn == id) {
            Thread.yield();
        }
    }

    private static void unlock(int id) {
        flag[id] = false;
    }

    private static class Consumer implements Runnable {
        @Override
        public void run() {
            while (timestamp > 0 || size > 0) {
                lock(CONSUMER_ID);
                // Critical Section Begin
                if (size > 0) {
                    int good = buffer[head];
                    head = (head + 1) % buffer.length;
                    --size;
                    System.out.println(good);
                }
                unlock(CONSUMER_ID);
            }
        }
    }

    private static class Producer implements Runnable {
        @Override
        public void run() {
            while (timestamp > 0) {
                lock(PRODUCER_ID);
                // Critical Section Begin
                if (size < buffer.length) {
                    buffer[(head + size++) % buffer.length] = timestamp--;
                }
                // Critical Section End
                unlock(PRODUCER_ID);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        buffer = new int[scanner.nextInt()];
        timestamp = scanner.nextInt();

        final Thread producer = new Thread(new Producer());
        final Thread consumer = new Thread(new Consumer());

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
