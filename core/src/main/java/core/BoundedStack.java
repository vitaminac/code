package core;

import java.util.function.Consumer;

public class BoundedStack<E> implements Stack<E> {
    private final ArrayList<E> list;

    public BoundedStack(int capacity) {
        this.list = new ArrayList<>(capacity);
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
        return this.list.size() == this.list.getCapacity();
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new RuntimeException("Stack is empty");
        return this.list.get(this.list.size() - 1);
    }

    @Override
    public void push(E element) {
        if (this.isFull()) throw new RuntimeException("Stack is full");
        this.list.insert(this.list.size(), element);
    }

    @Override
    public E pop() {
        if (this.isEmpty()) throw new RuntimeException("Stack is empty");
        return this.list.remove(this.list.size() - 1);
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        this.list.forEach(consumer);
    }
}
