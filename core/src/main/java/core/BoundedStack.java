package core;

import java.util.function.Consumer;

public class BoundedStack<E> implements Stack<E> {
    private final E[] elements;
    private int size = 0;
    private int top = 0;

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
    public E peek() {
        return this.elements[this.top];
    }

    @Override
    public void push(E element) {
        this.elements[this.top] = element;
        this.top = (this.top + 1) % this.elements.length;
        this.size = Math.min(size + 1, this.elements.length);
    }

    @Override
    public E pop() {
        this.top = (this.top - 1 + this.elements.length) % this.elements.length;
        E result = this.elements[this.top];
        this.elements[this.top] = null;
        this.size -= 1;
        return result;
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        for (int i = 0; i < this.size; i++) {
            consumer.accept(this.elements[(top - i + this.elements.length) % this.elements.length]);
        }
    }
}
