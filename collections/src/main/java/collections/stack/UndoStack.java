package collections.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

import core.util.Math;

public class UndoStack<E> implements Stack<E> {
    private final E[] elements;
    private int size = 0;
    private int top = -1;

    public UndoStack(int capacity) {
        @SuppressWarnings("unchecked") final var elements = (E[]) new Object[capacity];
        this.elements = elements;
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
        return this.elements[this.top];
    }

    @Override
    public void push(E element) {
        this.top = (this.top + 1) % this.elements.length;
        this.elements[this.top] = element;
        this.size = Math.min(size + 1, this.elements.length);
    }

    @Override
    public E pop() {
        E result = this.elements[this.top];
        this.elements[this.top] = null;
        this.top = (this.top - 1 + this.elements.length) % this.elements.length;
        this.size -= 1;
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < UndoStack.this.size;
            }

            @Override
            public E next() {
                if (this.index >= UndoStack.this.size) throw new NoSuchElementException();
                return UndoStack.this.elements[(UndoStack.this.top - this.index-- + UndoStack.this.elements.length) % UndoStack.this.elements.length];
            }
        };
    }
}
