package code.so;

// https://www.geeksforgeeks.org/petersons-algorithm-for-mutual-exclusion-set-1/
// https://www.geeksforgeeks.org/petersons-algorithm-for-mutual-exclusion-set-2-cpu-cycles-and-memory-fence/

import java.util.Scanner;

/**
 * Problem: Given 2 process i and j, you need to write a program
 * that can guarantee mutual exclusion between the two without any additional hardware support.
 * <p>
 * Solution: There can be multiple ways to solve this problem,
 * but most of them require additional hardware support.
 * <p>
 * The simplest and the most popular way to do this is by using Peterson Algorithm for mutual Exclusion.
 * It was developed by Peterson in 1981 though the initial work in this direction
 * by done by Theodorus Jozef Dekker who came up with Dekker’s algorithm in 1960,
 * which was later refined by Peterson and came to be known as Peterson’s Algorithm.
 * <p>
 * Basically, Peterson’s algorithm provides guaranteed mutual exclusion by using only the shared memory.
 * It uses two ideas in the algorithm,
 * <p>
 * Willingness to acquire lock.
 * Turn to acquire lock.
 * <p>
 * Wastage of CPU clock cycles
 * In layman terms, when a thread was waiting for its turn,
 * it ended in a long while loop which tested the condition millions of times per second
 * thus doing unnecessary computation.
 * There is a better way to wait, and it is known as “yield”.
 * <p>
 * To understand what it does, we need to dig deep into how the Process scheduler works in Linux.
 * The idea mentioned here is a simplified version of the scheduler,
 * the actual implementation has lots of complications.
 * <p>
 * Memory fence.
 * The code in earlier tutorial might have worked on most systems, but is was not 100% correct.
 * The logic was perfect, but most modern CPUs employ performance optimizations
 * that can result in out-of-order execution.
 * This reordering of memory operations (loads and stores)
 * normally goes unnoticed within a single thread of execution,
 * but can cause unpredictable behaviour in concurrent programs.
 */
public class PetersonMutualExclusion implements Runnable {
    private static int MAX;

    // https://stackoverflow.com/a/38636062/9980245
    private static volatile boolean[] flag = new boolean[2];
    private static volatile int turn;

    private static int answer = 0;

    // Initialize lock by reseting the desire of
    // both the threads to acquire the locks.
    // And, giving turn to one of them.
    private static void lock_init() {
        flag[0] = flag[1] = true;
        turn = 0;
    }

    // Executed before entering critical section
    private static void lock(int id) {
        // saying you want to acquire lock
        flag[id] = true;

        // But, first give the other thread the chance to
        // acquire lock
        turn = 1 - id;

        while (flag[1 - id] && turn == (1 - id)) {
            Thread.yield();
        }
    }

    private void unlock(int id) {
        // You do not desire to acquire lock in future.
        // This will allow the other thread to acquire
        // the lock.
        flag[id] = false;
    }

    private final int id;

    private PetersonMutualExclusion(int id) {
        this.id = id;
    }

    // A Sample function run by two threads created
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock(this.id);
            for (int j = MAX / 100; j > 0; j--) {
                // Critical section (Only one thread
                // can enter here at a time)
                answer++;
            }
            unlock(this.id);
        }
    }


    public static void main(String[] args) throws Exception {
        MAX = new Scanner(System.in).nextInt();
        final Thread thread1 = new Thread(new PetersonMutualExclusion(0));
        final Thread thread2 = new Thread(new PetersonMutualExclusion(1));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(answer);
    }
}
