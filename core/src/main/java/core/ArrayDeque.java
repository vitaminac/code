package core;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ArrayDeque<E> extends AbstractOrderedCollection<E> implements Deque<E> {
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
    public E getFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.elements[this.first];
    }

    @Override
    public E getLast() {
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
    public E removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        final E tmp = this.elements[this.first];
        this.elements[this.first] = null; // fix obsolete reference
        this.first = (this.first + 1) % this.elements.length;
        this.size--;
        return tmp;
    }

    @Override
    public E removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        this.size--;
        final E tmp = this.elements[this.first + this.size];
        this.elements[this.first + this.size] = null; // fix obsolete reference
        return tmp;
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        for (int i = 0; i < this.size; i++) {
            consumer.accept(this.elements[(this.first + i) % this.elements.length]);
        }
    }
}
