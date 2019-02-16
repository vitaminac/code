package code.adt.list;

import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 8;

    private E[] elements;
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
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E get(int index) {
        this.checkIndex(index);
        return this.elements[index];
    }

    @Override
    public void set(int index, E element) {
        this.checkIndex(index);
        this.elements[index] = element;
    }

    @Override
    public void add(int index, E element) {
        this.checkIndexForAdd(index);
        // double size of array if necessary
        if (index == this.elements.length) {
            this.resize(this.elements.length * 2);
        }
        for (int i = this.size++; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
    }

    public void add(E element) {
        this.add(this.size, element);
    }

    @Override
    public E remove(int index) {
        this.checkIndex(index);
        E returnVal = this.elements[index];
        --this.size;
        for (; index < size; index++) {
            this.elements[index] = this.elements[index + 1];
        }
        // shrink size of array if necessary
        if (this.size > 0 && this.size == this.elements.length / 4) resize(this.elements.length / 2);
        return returnVal;
    }

    public E remove() {
        return this.remove(this.size - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < ArrayList.this.size;
            }

            @Override
            public E next() {
                return ArrayList.this.get(this.index);
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
        E[] old = this.elements;
        this.elements = (E[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            this.elements[i] = old[i];
        }
    }
}
