package algs4.fundamentals.stacks;

import collections.deque.LinkedListSteque;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.queue.Queue;

import java.util.Scanner;

public class WebEx8TagSystem {
    public static void main(String[] args) {
        Queue<Integer> queue = Queue.fromSteque(() -> new LinkedListSteque<>(SinglyLinkedListDoubleReference::new));
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine().chars().forEach(queue::enqueue);

        while (queue.size() >= 3) {
            int c = queue.dequeue();
            System.out.print((char) c);
            System.out.print((char) (int) queue.dequeue());
            System.out.print((char) (int) queue.dequeue());
            System.out.flush();
            if (c == '0') {
                queue.enqueue((int) '1');
                queue.enqueue((int) '0');
            } else if (c == '1') {
                queue.enqueue((int) '0');
                queue.enqueue((int) '1');
                queue.enqueue((int) '0');
            }
        }
    }
}
