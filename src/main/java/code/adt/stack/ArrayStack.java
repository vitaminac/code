package code.adt.stack;

import java.util.Iterator;
import java.util.Scanner;

public class ArrayStack<E> implements Stack<E> {
    private final E[] stack;
    private int last = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public ArrayStack(int size) {
        this.stack = (E[]) new Object[size];
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
    public E peek() {
        if (!this.isEmpty())
            return this.stack[this.last];
        else throw new RuntimeException("The stack is empty");
    }

    @Override
    public void push(E element) {
        if (size == stack.length) {
            throw new RuntimeException("The stack is full");
        }
        this.last = (this.last + 1) % this.stack.length;
        this.stack[this.last] = element;
        ++this.size;
    }

    @Override
    public E pop() {
        if (this.isEmpty())
            throw new RuntimeException("The stack is empty");
        else {
            final E item = this.stack[this.last];
            this.last = (this.last - 1 + this.stack.length) % this.stack.length;
            --this.size;
            return item;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int remain = ArrayStack.this.size;
            private int nextIndex = ArrayStack.this.last;

            @Override
            public boolean hasNext() {
                return this.remain > 0;
            }

            @Override
            public E next() {
                this.remain--;
                E item = ArrayStack.this.stack[this.nextIndex];
                this.nextIndex = (this.nextIndex - 1 + ArrayStack.this.stack.length) % ArrayStack.this.stack.length;
                return item;
            }
        };
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<>(5);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            stack.push(scanner.nextInt());
        }
        for (int i : stack) {
            System.out.println(i);
        }
    }
}
