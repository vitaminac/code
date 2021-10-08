package core.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

import core.list.ArrayList;

public class BoundedStack<E> implements Stack<E> {
    private final ArrayList<E> list;
    private final int capacity;

    public BoundedStack(int capacity) {
        this.capacity = capacity;
        this.list = new ArrayList<>(this.capacity);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public boolean isFull() {
        return this.list.size() == this.capacity;
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new RuntimeException("Stack is empty");
        return this.list.get(this.list.size() - 1);
    }

    @Override
    public void push(E element) {
        if (this.isFull()) throw new RuntimeException("Stack is full");
        this.list.add(this.list.size(), element);
    }

    @Override
    public E pop() {
        if (this.isEmpty()) throw new RuntimeException("Stack is empty");
        return this.list.remove(this.list.size() - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = BoundedStack.this.size() - 1;

            @Override
            public boolean hasNext() {
                return this.index >= 0;
            }

            @Override
            public E next() {
                if (this.index < 0) throw new NoSuchElementException();
                return BoundedStack.this.list.get(this.index--);
            }
        };
    }
}
