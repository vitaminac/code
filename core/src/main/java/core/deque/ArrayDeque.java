package core.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Deque<E> {
    private static final int DEFAULT_CAPACITY = 8;
    private E[] elements;
    private int first = 0;
    private int size = 0;

    public ArrayDeque() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayDeque(int capacity) {
        @SuppressWarnings("unchecked") final var elements = (E[]) new Object[capacity];
        this.elements = elements;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    private boolean isFull() {
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
        if (this.isFull()) {
            this.resize(this.elements.length * 2);
        }
        this.first = (this.first - 1 + this.elements.length) % this.elements.length;
        this.elements[this.first] = element;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.isFull()) {
            this.resize(this.elements.length * 2);
        }
        this.elements[(this.first + this.size++) % this.elements.length] = element;
    }

    @Override
    public E removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        final E tmp = this.elements[this.first];
        this.elements[this.first] = null; // fix obsolete reference
        this.first = (this.first + 1) % this.elements.length;
        this.size--;
        if (this.size > 0 && this.size == this.elements.length / 4) {
            resize(this.elements.length / 2);
        }
        return tmp;
    }

    @Override
    public E removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        this.size--;
        final E tmp = this.elements[this.first + this.size];
        this.elements[this.first + this.size] = null; // fix obsolete reference
        if (this.size > 0 && this.size == this.elements.length / 4) {
            resize(this.elements.length / 2);
        }
        return tmp;
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked") final var spaces = (E[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            spaces[i] = this.elements[this.first + i % this.elements.length];
        }
        this.elements = spaces;
        this.first = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < ArrayDeque.this.size();
            }

            @Override
            public E next() {
                if (this.index >= ArrayDeque.this.size()) throw new NoSuchElementException();
                return ArrayDeque.this.elements[(ArrayDeque.this.first + this.index++) % ArrayDeque.this.elements.length];
            }
        };
    }
}
