package code.so;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/operating-system-bakery-algorithm/
 * <p>
 * The Bakery algorithm is one of the simplest known solutions
 * to the mutual exclusion problem for the general case of N process.
 * <p>
 * Bakery Algorithm is a critical section solution for N processes.
 * The algorithm preserves the first come first serve property.
 * <p>
 * Before entering its critical section,
 * the process receives a number.
 * Holder of the smallest number enters the critical section.
 * <p>
 * If processes Pi and Pj receive the same number,
 * if i < j
 * Pi is served first;
 * else
 * Pj is served first.
 * <p>
 * The numbering scheme always generates numbers in increasing order of enumeration
 * <p>
 * Firstly the ticket number is compared.
 * If same then the process ID is compared next
 * <p>
 * Shared data
 * choosing is an array [0..n – 1] of boolean values;
 * number is an array [0..n – 1] of integer values.
 * Both are initialized to False & Zero respectively.
 * <p>
 * Firstly the process sets its “choosing” variable to be TRUE indicating its intent to enter critical section.
 * Then it gets assigned the highest ticket number corresponding to other processes.
 * Then the “choosing” variable is set to FALSE indicating that it now has a new ticket number.
 * This is in-fact the most important and confusing part of the algorithm.
 * <p>
 * It is actually a small critical section in itself
 * The very purpose of the first three lines is that
 * if a process is modifying its TICKET value
 * then at that time some other process
 * should not be allowed to check its old ticket value which is now obsolete.
 * <p>
 * This is why inside the for loop before checking ticket value
 * we first make sure that all other processes have the “choosing” variable as FALSE
 * <p>
 * After that we proceed to check the ticket values of processes
 * where process with least ticket number/process id gets inside the critical section.
 * The exit section just resets the ticket value to zero
 */
public class BakeryAlgorithm implements Runnable {
    private static class Choosing {
        private volatile boolean c0;
        private volatile boolean c1;
        private volatile boolean c2;
        private volatile boolean c3;
        private volatile boolean c4;
        private volatile boolean c5;
        private volatile boolean c6;
        private volatile boolean c7;
        private volatile boolean c8;
        private volatile boolean c9;

        private boolean get(int index) {
            switch (index) {
                case 0:
                    return c0;
                case 1:
                    return c1;
                case 2:
                    return c2;
                case 3:
                    return c3;
                case 4:
                    return c4;
                case 5:
                    return c5;
                case 6:
                    return c6;
                case 7:
                    return c7;
                case 8:
                    return c8;
                case 9:
                    return c9;
                default:
                    throw new RuntimeException();
            }
        }

        private void set(int index, boolean value) {
            switch (index) {
                case 0:
                    c0 = value;
                    break;
                case 1:
                    c1 = value;
                    break;
                case 2:
                    c2 = value;
                    break;
                case 3:
                    c3 = value;
                    break;
                case 4:
                    c4 = value;
                    break;
                case 5:
                    c5 = value;
                    break;
                case 6:
                    c6 = value;
                    break;
                case 7:
                    c7 = value;
                    break;
                case 8:
                    c8 = value;
                    break;
                case 9:
                    c9 = value;
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }

    private static class Tickets {
        private volatile int c0;
        private volatile int c1;
        private volatile int c2;
        private volatile int c3;
        private volatile int c4;
        private volatile int c5;
        private volatile int c6;
        private volatile int c7;
        private volatile int c8;
        private volatile int c9;

        private int get(int index) {
            switch (index) {
                case 0:
                    return c0;
                case 1:
                    return c1;
                case 2:
                    return c2;
                case 3:
                    return c3;
                case 4:
                    return c4;
                case 5:
                    return c5;
                case 6:
                    return c6;
                case 7:
                    return c7;
                case 8:
                    return c8;
                case 9:
                    return c9;
                default:
                    throw new RuntimeException();
            }
        }

        private void set(int index, int value) {
            switch (index) {
                case 0:
                    c0 = value;
                    break;
                case 1:
                    c1 = value;
                    break;
                case 2:
                    c2 = value;
                    break;
                case 3:
                    c3 = value;
                    break;
                case 4:
                    c4 = value;
                    break;
                case 5:
                    c5 = value;
                    break;
                case 6:
                    c6 = value;
                    break;
                case 7:
                    c7 = value;
                    break;
                case 8:
                    c8 = value;
                    break;
                case 9:
                    c9 = value;
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }

    private static final int NUM_THREAD = 10;
    // we need an array of volatile element
    private static Choosing choosing;
    private static Tickets tickets;
    private static int iteration;

    private static volatile int resource;

    private final int id;

    private BakeryAlgorithm(final int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < iteration; i++) {
            lock(this.id);
            resource++;
            unlock(this.id);
        }
    }

    private static void lock(int thread) {
        choosing.set(thread, true);

        int max_ticket = 0;
        // Finding Maximum ticket value among current threads
        for (int i = 0; i < NUM_THREAD; ++i) {
            max_ticket = tickets.get(i) > max_ticket ? tickets.get(i) : max_ticket;
        }

        // Allotting a new ticket value as MAXIMUM + 1
        tickets.set(thread, max_ticket + 1);

        choosing.set(thread, false);

        // The ENTRY Section starts from here
        for (int other = 0; other < NUM_THREAD; ++other) {

            // Applying the bakery algorithm conditions
            while (choosing.get(other)) {
                Thread.yield();
            }

            while (tickets.get(other) != 0 && (tickets.get(other)
                    < tickets.get(thread)
                    || (tickets.get(other)
                    == tickets.get(thread)
                    && other < thread))) {
                Thread.yield();
            }
        }
    }

    private static void unlock(int thread) {
        tickets.set(thread, 0);
    }

    public static void main(String[] args) throws Exception {
        iteration = new Scanner(System.in).nextInt();
        choosing = new Choosing();
        tickets = new Tickets();
        resource = 0;
        Thread[] threads = new Thread[NUM_THREAD];

        for (int i = 0; i < NUM_THREAD; i++) {
            threads[i] = new Thread(new BakeryAlgorithm(i));
            threads[i].start();
        }

        for (int i = 0; i < NUM_THREAD; i++) {
            threads[i].join();
        }

        System.out.println(resource);
    }
}
