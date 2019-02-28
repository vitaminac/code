package code.adt.stack;

import code.adt.Stack;

import java.util.Iterator;
import java.util.Scanner;

public class BoundedStack<E> implements Stack<E> {
    private final E[] stack;
    private int top = -1;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public BoundedStack(int top) {
        this.stack = (E[]) new Object[top];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean isFull() {
        return this.size == this.stack.length;
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new RuntimeException("The stack is empty");
        return this.stack[this.top];
    }

    @Override
    public void push(E element) {
        this.top = (this.top + 1) % this.stack.length;
        this.stack[this.top] = element;
        if (this.size < this.stack.length) ++this.size;
    }

    @Override
    public E pop() {
        if (this.isEmpty())
            throw new RuntimeException("The stack is empty");
        else {
            final E returnVal = this.stack[this.top];
            this.top = (this.top - 1 + this.stack.length) & this.stack.length;
            --this.size;
            return returnVal;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = BoundedStack.this.top;
            private int remain = BoundedStack.this.size;

            @Override
            public boolean hasNext() {
                return this.remain > 0;
            }

            @Override
            public E next() {
                final E returnVal = BoundedStack.this.stack[this.current];
                this.current = (this.current - 1 + BoundedStack.this.stack.length) % BoundedStack.this.stack.length;
                --this.remain;
                return returnVal;
            }
        };
    }

    public static void main(String[] args) {
        Stack<String> stack = new BoundedStack<>(5);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            stack.push(scanner.next());
        }
        for (String str : stack) {
            System.out.println(str);
        }
    }
}
