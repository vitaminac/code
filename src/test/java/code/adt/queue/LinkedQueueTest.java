package code.adt.queue;

import code.adt.LinkedList;
import code.adt.Queue;

import java.util.Scanner;

public class LinkedQueueTest {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                System.out.print(queue.dequeue() + " ");
        }
        System.out.println("(" + queue.size() + " left on queue)");
    }
}