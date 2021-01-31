package algs4.fundamentals.stacks;

import core.queue.Queue;

import java.util.Scanner;

public class WebEx11StreamingAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        Queue<Integer> queue = Queue.fromSinglyLinkedListDoubleReference();
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
