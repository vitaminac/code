package algs4.fundamentals.stacks.exercises;

import java.util.Scanner;

import core.queue.Queue;
import core.stack.Stack;

public class Ex3VerifyIntermixedStackOperationSequence {
    public static void main(String[] args) {
        try (final var sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                final var queue = Queue.<Integer>fromSinglyLinkedListDoubleReference();
                final var stack = Stack.<Integer>fromSinglyLinkedListDoubleReference();
                for (int i = 0; i <= 9; i++) {
                    queue.enqueue(sc.nextInt());
                }
                for (int i = 0; i <= 9; i++) {
                    stack.push(i);
                    while (!stack.isEmpty() && stack.peek().equals(queue.peek())) {
                        stack.pop();
                        queue.dequeue();
                    }
                }
                System.out.println(queue.isEmpty());
            }
        }
    }
}
