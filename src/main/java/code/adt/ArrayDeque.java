package code.adt;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ArrayDeque<E> implements Deque<E> {
    private final E[] elements;
    private int first = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public ArrayDeque(int capacity) {
        this.elements = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean isFull() {
        return this.size >= this.elements.length;
    }

    @Override
    public E first() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.elements[this.first];
    }

    @Override
    public E last() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.elements[(this.first + this.size - 1) % this.elements.length];
    }

    @Override
    public void addFirst(E element) {
        if (this.isFull()) throw new RuntimeException("Capacity Constraints Violation");
        this.first = (this.first - 1 + this.elements.length) % this.elements.length;
        this.elements[this.first] = element;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.isFull()) throw new RuntimeException("Capacity Constraints Violation");
        this.elements[(this.first + this.size++) % this.elements.length] = element;
    }

    @Override
    public void removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        this.first = (this.first + 1) % this.elements.length;
        this.size--;
    }

    @Override
    public void removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        this.size--;
    }

    @Override
    public void enumerate(Consumer<E> consumer) {
        for (int i = 0; i < this.size; i++) {
            consumer.accept(this.elements[(this.first + i) % this.elements.length]);
        }
    }
}
