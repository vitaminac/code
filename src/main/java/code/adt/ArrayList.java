package code.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E>, Stack<E>, Queue<E>, Bag<E> {
    private static final int DEFAULT_CAPACITY = 8;

    private E[] elements;
    private int first = 0;
    private int size = 0;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        this.elements = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(E item) {
        this.push(item);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E get(int index) {
        this.checkIndex(index);
        return this.elements[(this.first + this.elements.length + index) % this.elements.length];
    }

    @Override
    public void set(int index, E element) {
        this.checkIndex(index);
        this.elements[(this.first + index) % this.elements.length] = element;
    }

    @Override
    public void add(int index, E element) {
        this.checkIndexForAdd(index);
        // double size of array if necessary
        if (this.size == this.elements.length) {
            this.resize(this.elements.length * 2);
        }
        for (int i = this.size++; i > index; i--) {
            this.set(i, this.get(i - 1));
        }
        this.set(index, element);
    }

    @Override
    public E remove(int index) {
        this.checkIndex(index);
        E returnVal = this.get(index);
        --this.size;
        for (; index < size; index++) {
            this.set(index, this.get(index + 1));
        }
        // shrink size of array if necessary
        if (this.size > 0 && this.size == this.elements.length / 4) resize(this.elements.length / 2);
        return returnVal;
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.get(this.size() - 1);
    }

    @Override
    public void enqueue(E element) {
        if (this.size++ >= this.elements.length) this.resize(2 * this.elements.length);
        this.first = (this.first - 1 + this.elements.length) % this.elements.length;
        this.set(0, element);
    }

    @Override
    public E dequeue() {
        return this.remove(this.size - 1);
    }

    @Override
    public void push(E element) {
        this.add(this.size, element);
    }

    @Override
    public E pop() {
        return this.remove(this.size - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int remain = ArrayList.this.size;

            @Override
            public boolean hasNext() {
                return this.remain > 0;
            }

            @Override
            public E next() {
                return ArrayList.this.get(--this.remain);
            }
        };
    }

    private void checkIndex(int index, int size) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkIndex(int index) {
        this.checkIndex(index, this.size);
    }

    private void checkIndexForAdd(int index) {
        this.checkIndex(index, this.size + 1);
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] spaces = (E[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            spaces[i] = this.get(i);
        }
        this.elements = spaces;
        this.first = 0;
    }
}
