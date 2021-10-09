package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;

/**
 * https://algs4.cs.princeton.edu/13stacks/Queue.java.html
 */
public class Queue {
    public static void main(String[] args) {
        core.queue.Queue<String> queue = core.queue.Queue.fromSinglyLinkedListDoubleReference();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }
}
