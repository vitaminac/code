package algs4.fundamentals.stacks;

import java.util.Scanner;

import collections.deque.LinkedListSteque;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.queue.Queue;

public class WebEx11StreamingAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        Queue<Integer> queue = Queue.fromSteque(() -> new LinkedListSteque<>(SinglyLinkedListDoubleReference::new));
        while (scanner.hasNext()) {
            queue.enqueue(scanner.nextInt());
            if (queue.size() > k) {
                queue.dequeue();
            }
        }

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}
