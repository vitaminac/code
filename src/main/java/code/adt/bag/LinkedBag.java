package code.adt.bag;

import code.adt.LinkedNode;

import java.util.Iterator;
import java.util.Scanner;

public class LinkedBag<E> implements Bag<E> {
    private LinkedNode<E> head;
    private LinkedNode<E> tail;
    private int size = 0;

    public LinkedBag() {
        LinkedNode<E> node = new LinkedNode<>(null);
        this.head = node;
        this.tail = node;
    }

    @Override
    public void add(E item) {
        this.tail.next = new LinkedNode<>(item);
        this.tail = this.tail.next;
        ++this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedNode<E> next = LinkedBag.this.head.next;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                E item = next.element;
                this.next = next.next;
                return item;
            }
        };
    }

    public static void main(String[] args) {
        Bag<Double> numbers = new LinkedBag<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            numbers.add(scanner.nextDouble());
        }

        int n = numbers.size();

        // compute sample mean
        double sum = 0.0;
        for (double x : numbers)
            sum += x;
        double mean = sum / n;

        // compute sample standard deviation
        sum = 0.0;
        for (double x : numbers) {
            sum += (x - mean) * (x - mean);
        }

        double stddev = Math.sqrt(sum / (n - 1));

        System.out.printf("Mean:    %.2f\n", mean);
        System.out.printf("Std dev: %.2f\n", stddev);
    }
}
