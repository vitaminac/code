package code.geeksforgeeks.concurrent;

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
public class BakeryAlgorithm<T> {
    public interface Choosing {
        boolean get(int index);

        void set(int index, boolean value);
    }

    public interface Tickets {
        int get(int index);

        void set(int index, int value);
    }

    public interface Resource<T> {
        T get();

        void set(T value);
    }

    private final int numThread;
    // we need an array of volatile element
    private final Choosing choosing;
    private final Tickets tickets;

    private final Resource<T> resource;

    public BakeryAlgorithm(Resource<T> resource, int numThread, Choosing choosing, Tickets tickets) {
        this.resource = resource;
        this.numThread = numThread;
        this.choosing = choosing;
        this.tickets = tickets;
    }

    public void lock(int thread) {
        choosing.set(thread, true);

        int max_ticket = 0;
        // Finding Maximum ticket value among current threads
        for (int i = 0; i < numThread; ++i) {
            max_ticket = tickets.get(i) > max_ticket ? tickets.get(i) : max_ticket;
        }

        // Allotting a new ticket value as MAXIMUM + 1
        tickets.set(thread, max_ticket + 1);

        choosing.set(thread, false);

        // The ENTRY Section starts from here
        for (int other = 0; other < numThread; ++other) {

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

    public void unlock(int thread) {
        tickets.set(thread, 0);
    }

    public Resource<T> getResource() {
        return this.resource;
    }
}
