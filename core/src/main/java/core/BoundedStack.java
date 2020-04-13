package core;

public class BoundedStack<E> implements Stack<E> {
    private final E[] elements;
    private int size = 0;
    private int index = 0;

    @SuppressWarnings("unchecked")
    public BoundedStack(int capacity) {
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
    public E top() {
        return this.elements[this.index];
    }

    @Override
    public void push(E element) {
        this.elements[this.index] = element;
        this.index = (this.index + 1) % this.elements.length;
        this.size = Math.min(size + 1, this.elements.length);
    }

    @Override
    public E pop() {
        this.index = (this.index - 1 + this.elements.length) % this.elements.length;
        E result = this.elements[this.index];
        this.elements[this.index] = null;
        this.size -= 1;
        return result;
    }
}
