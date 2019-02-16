package code.adt.queue;

import java.util.Scanner;

public class Tail {
    public static void main(String[] args) {
        Queue<String> queue = new ResizingArrayQueue<>();
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
