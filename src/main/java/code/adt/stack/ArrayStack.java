package code.adt.stack;

import java.util.Iterator;
import java.util.Scanner;

public class ArrayStack<E> implements Stack<E> {
    private final E[] stack;
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

    public boolean isFull() {
        return this.size == this.stack.length;
    }

    @Override
    public E peek() {
        if (!this.isEmpty()) throw new RuntimeException("The stack is empty");
        return this.stack[this.size - 1];
    }

    @Override
    public void push(E element) {
        if (this.isFull()) {
            throw new RuntimeException("The stack is full");
        }
        this.stack[this.size++] = element;
    }

    @Override
    public E pop() {
        if (this.isEmpty())
            throw new RuntimeException("The stack is empty");
        else {
            return this.stack[--this.size];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = ArrayStack.this.size;

            @Override
            public boolean hasNext() {
                return this.index > 0;
            }

            @Override
            public E next() {
                return ArrayStack.this.stack[--this.index];
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
