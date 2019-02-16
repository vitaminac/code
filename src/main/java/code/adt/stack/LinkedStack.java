package code.adt.stack;

import code.adt.LinkedNode;

import java.util.Iterator;
import java.util.Scanner;

public class LinkedStack<E> implements Stack<E> {

    private LinkedNode<E> top;
    private int size;

    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }

    public LinkedStack(Stack<E> stack) {
        this.size = stack.size();
        if (!stack.isEmpty()) {
            this.top = new LinkedNode<>(stack.pop());
            LinkedNode<E> current = this.top;
            while (!stack.isEmpty()) {
                current.next = new LinkedNode<>(stack.pop());
                current = current.next;
            }
        }
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.top == null;
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new RuntimeException("The Queue is empty");
        return this.top.element;
    }

    @Override
    public void push(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        node.next = this.top;
        this.top = node;
        this.size++;
    }

    @Override
    public E pop() {
        if (this.isEmpty()) throw new RuntimeException("The Queue is empty");
        E item = this.top.element;
        this.top = this.top.next;
        this.size--;
        return item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedNode<E> current = LinkedStack.this.top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E item = current.element;
                current = current.next;
                return item;
            }
        };
    }

    public static void main(String[] args) {
        Stack<String> stack = new LinkedStack<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                System.out.print(stack.pop() + " ");
        }
        System.out.println("(" + stack.size() + " left on stack)");
    }
}
