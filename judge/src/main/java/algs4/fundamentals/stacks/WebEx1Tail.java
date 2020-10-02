package algs4.fundamentals.stacks;

import core.Queue;
import core.SinglyLinkedList;

import java.util.Scanner;

public class WebEx1Tail {
    public static void main(String[] args) {
        Queue<String> queue = new SinglyLinkedList<>();
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        while (scanner.hasNextLine()) {
            queue.enqueue(scanner.nextLine());
            while (queue.size() > k) {
                queue.dequeue();
            }
        }
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}
