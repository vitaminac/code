package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import core.deque.ArrayDeque;
import core.queue.Queue;

/**
 * https://algs4.cs.princeton.edu/13stacks/ResizingArrayQueue.java.html
 */
public class ResizingArrayQueue {
    public static void main(String[] args) {
        final Queue<String> queue = Queue.fromDeque(ArrayDeque::new);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }
}
