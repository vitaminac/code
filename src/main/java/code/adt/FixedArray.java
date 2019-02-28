package code.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedArray<E> implements Stack<E> {
    private final E[] elements;
    private int first = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public FixedArray(int n) {
        this.elements = (E[]) new Object[n];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.elements.length;
    }

    @Override
    public E peek() {
        return this.elements[(this.first + this.size - 1) % this.elements.length];
    }

    @Override
    public void push(E element) {
        if (this.isFull()) throw new RuntimeException();
        this.elements[(this.first + this.size++) % this.elements.length] = element;
    }

    @Override
    public E pop() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.elements[(this.first + --this.size) % this.elements.length];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int first = FixedArray.this.first;
            private int remain = FixedArray.this.size;

            @Override
            public boolean hasNext() {
                return this.remain > 0;
            }

            @Override
            public E next() {
                return FixedArray.this.elements[(this.first + --this.remain) % FixedArray.this.elements.length];
            }
        };
    }
}
