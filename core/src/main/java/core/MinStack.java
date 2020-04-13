package core;

import java.util.Comparator;
import java.util.function.Supplier;

public class MinStack<E> implements Stack<E> {
    private final Comparator<E> comparator;
    private Stack<E> stack;
    private Stack<E> minStack;

    public MinStack(Comparator<E> comparator, Supplier<Stack<E>> factory) {
        this.comparator = comparator;
        this.stack = factory.get();
        this.minStack = factory.get();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public E top() {
        return this.stack.top();
    }

    public E min() {
        return this.minStack.top();
    }

    @Override
    public void push(E e) {
        if (this.minStack.isEmpty() || this.comparator.compare(e, this.minStack.top()) <= 0) {
            this.minStack.push(e);
        }
        this.stack.push(e);
    }

    @Override
    public E pop() {
        E e = this.stack.pop();
        if (this.comparator.compare(e, this.minStack.top()) == 0) this.minStack.pop();
        return e;
    }
}
