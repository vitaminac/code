package code.adt;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ArrayList<E> implements List<E>, Deque<E>, Stack<E>, Queue<E>, Bag<E>, Cloneable {
    private static final int DEFAULT_CAPACITY = 8;

    private E[] elements;
    private int first;
    private int size;

    private ArrayList(E[] elements, int first, int size) {
        this.elements = elements;
        this.first = first;
        this.size = size;
    }

    public ArrayList(E... elements) {
        this(elements, 0, elements.length);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        this((E[]) new Object[capacity], 0, 0);
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public void addFirst(E element) {
        if (this.size++ >= this.elements.length)
            this.resize(2 * this.elements.length);
        this.first = (this.first - 1 + this.elements.length) % this.elements.length;
        this.set(0, element);
    }

    @Override
    public void addLast(E element) {
        this.add(this.size, element);
    }

    @Override
    public E removeFirst() {
        E retVal = this.get(0);
        this.first = (this.first + 1) % this.elements.length;
        --this.size;
        return retVal;
    }

    @Override
    public E removeLast() {
        return this.remove(this.size - 1);
    }

    @Override
    public E first() {
        return this.get(0);
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.get(this.size() - 1);
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
        for (; index < size - 1; index++) {
            this.set(index, this.get(index + 1));
        }
        --this.size;
        // shrink size of array if necessary
        if (this.size > 0 && this.size == this.elements.length / 4)
            resize(this.elements.length / 2);
        return returnVal;
    }

    @Override
    public int find(E element) {
        for (int i = 0; i < this.size(); i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E peek() {
        return this.last();
    }

    @Override
    public void enqueue(E element) {
        this.addFirst(element);
    }

    @Override
    public E dequeue() {
        return this.removeLast();
    }

    @Override
    public void push(E element) {
        this.addLast(element);
    }

    @Override
    public E pop() {
        try {
            return this.removeLast();
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void enumerate(Consumer<E> consumer) {
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i));
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int remain = ArrayList.this.size;
            private final int originalSize = ArrayList.this.size;

            @Override
            public boolean hasNext() {
                return this.remain > 0;
            }

            @Override
            public E next() {
                /**
                 * Modify the iterator code to immediately throw a java.util.ConcurrentModificationException
                 * if the client modifies the collection (via push() or pop()) during iteration.
                 * Solution: Maintain a counter that counts the number of push() and pop() operations.
                 * When creating an iterator, store this value as an iterator instance variable.
                 * Before each call to hasNext() and next(),
                 * check that this value has not changed
                 * since construction of the iterator; if it has, throw an exception.
                 */
                if (originalSize != ArrayList.this.size) throw new ConcurrentModificationException();
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

    @Override
    public ArrayList<E> clone() {
        return new ArrayList<>(Arrays.copyOf(this.elements, this.elements.length), this.first, this.size);
    }

    public ArrayList<E> concatenate(Iterable<E> iterable) {
        ArrayList<E> list = new ArrayList<>();
        for (E e : this) {
            list.add(e);
        }
        for (E e : iterable) {
            list.add(e);
        }
        return list;
    }
}
