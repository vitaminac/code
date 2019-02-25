package code.adt;

import code.adt.queue.Queue;
import code.adt.queue.ResizingArrayQueue;
import code.adt.stack.ResizingArrayStack;
import code.adt.stack.Stack;

import java.util.Scanner;

/**
 * Given a stack of an unknown number of strings,
 * print out the 5th to the last one.
 *
 * It's OK to destroy the stack in the process.
 * Hint: use a queue of 5 elements.
 */
public class PrintLastFifth {
    public static void main(String[] args) {
        Stack<Integer> stack = new ResizingArrayStack<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            stack.push(scanner.nextInt());
        }

        Queue<Integer> queue = new ResizingArrayQueue<>();
        while (!stack.isEmpty()) {
            if (queue.size() >= 5) {
                queue.dequeue();
            }
            queue.enqueue(stack.pop());
        }
        System.out.println(queue.dequeue());
    }
}
