package core.stack;

import core.Math;

public class UndoStack<E> implements Stack<E> {
    private final E[] elements;
    private int size = 0;
    private int top = -1;

    @SuppressWarnings("unchecked")
    public UndoStack(int capacity) {
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
}
