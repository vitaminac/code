package code.adt.queue;

import code.adt.ArrayList;
import code.adt.Queue;

import java.util.Scanner;

public class StreamingAlgorithm<E> {
    private Queue<E> queue;
    private int k;

    public StreamingAlgorithm(int k) {
        this.queue = new ArrayList<>();
        this.k = k;
    }

    public void add(E element) {
        if (this.queue.size() >= this.k) {
            this.queue.dequeue();
        }
        this.queue.enqueue(element);
    }

    public Iterable<E> getLastKItems() {
        return this.queue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        final StreamingAlgorithm<Integer> stream = new StreamingAlgorithm<>(k);
        while (scanner.hasNext()) {
            stream.add(scanner.nextInt());
        }

        for (int i : stream.getLastKItems()) {
            System.out.println(i);
        }
    }
}
