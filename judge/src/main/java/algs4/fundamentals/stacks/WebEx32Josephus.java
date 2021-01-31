package algs4.fundamentals.stacks;

import core.queue.Queue;

import java.util.Scanner;

/**
 * In the Josephus problem from antiquity,
 * N people are in dire straits and agree to the following strategy to reduce the population.
 *
 * They arrange themselves in a circle (at positions numbered from 0 to N-1) and proceed around the circle,
 * eliminating every Mth person until only one person is left.
 *
 * Legend has it that Josephus figured out where to sit to avoid being eliminated.
 * Write a Queue client Josephus.java that takes M and N from the command line
 * and prints out the order in which people are eliminated
 * (and thus would show Josephus where to sit in the circle).
 */
public class WebEx32Josephus {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();

        // initialize the queue
        Queue<Integer> queue = Queue.fromSinglyLinkedListDoubleReference();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);

        while (!queue.isEmpty()) {
            for (int i = 0; i < m - 1; i++)
                queue.enqueue(queue.dequeue());
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();
    }
}
