package algs4.fundamentals.stacks.creative;

import java.util.Scanner;

import collections.deque.LinkedListSteque;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.queue.Queue;
import collections.stack.Stack;

public class CreativeProblem45VerifyIntermixedStackOperationSequence {
    public static void main(String[] args) {
        try (final var sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int N = sc.nextInt();
                final var queue = Queue.<Integer>fromSteque(() -> new LinkedListSteque<>(SinglyLinkedListDoubleReference::new));
                final var stack = Stack.<Integer>fromSteque(() -> new LinkedListSteque<>(SinglyLinkedListDoubleReference::new));
                for (int i = 0; i < N; i++) {
                    queue.enqueue(sc.nextInt());
                }
                for (int i = 0; i < N; i++) {
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
