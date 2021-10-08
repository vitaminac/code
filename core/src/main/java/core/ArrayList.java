package core;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 8;

    private E[] elements;
    private int size;

    private ArrayList(E[] elements, int size) {
        this.elements = elements;
        this.size = size;
    }

    @SafeVarargs
    public ArrayList(E... elements) {
        this(elements, elements.length);
    }

    public ArrayList(ArrayList<E> list) {
        this(Arrays.copyFrom(list.elements, 0, list.elements.length), list.size);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        this((E[]) new Object[capacity], 0);
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size; i++) this.elements[i] = null; // remove obsolete reference
        this.size = 0;
    }

    @Override
    public E get(int index) {
        this.checkIndex(index);
        return this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        this.checkIndex(index);
        E retVal = this.elements[index];
        this.elements[index] = element;
        return retVal;
    }

    @Override
    public void add(int index, E element) {
        this.checkIndexForAdd(index);
        // double size of array if necessary
        if (this.size == this.elements.length) {
            this.resize(this.elements.length * 2);
        }
        for (int i = this.size++; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
    }

    @Override
    public E remove(int index) {
        this.checkIndex(index);
        E returnVal = this.elements[index];
        this.size -= 1;
        for (; index < size - 1; index++) {
            this.elements[index] = this.elements[index + 1];
        }
        this.elements[this.size] = null; // fix obsolete reference
        // shrink size of array if necessary
        if (this.size > 0 && this.size == this.elements.length / 4)
            resize(this.elements.length / 2);
        return returnVal;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size(); i++) if (this.elements[i].equals(element)) return i;
        return -1;
    }

    // Returns the index of the last occurrence of the specified element
    public int lastIndexOf(E element) {
        for (int i = this.size; i > 0; i--) if (this.elements[i].equals(element)) return i;
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < ArrayList.this.size();
            }

            @Override
            public E next() {
                if (this.index >= ArrayList.this.size()) throw new NoSuchElementException();
                return ArrayList.this.get(this.index++);
            }
        };
    }

    public E[] toArray() {
        return Arrays.copyFrom(this.elements, 0, this.size);
    }

    private int getCapacity() {
        return this.elements.length;
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

    private void resize(int capacity) {
        @SuppressWarnings("unchecked") E[] spaces = (E[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            spaces[i] = this.elements[i];
        }
        this.elements = spaces;
    }
}
