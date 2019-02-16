package code.adt.stack;

import code.adt.LinkedNode;

import java.util.Iterator;
import java.util.Scanner;

public class LinkedStack<E> implements Stack<E> {

    private LinkedNode<E> head = null;
    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public E peek() {
        if (this.isEmpty()) throw new RuntimeException("The Queue is empty");
        return this.head.element;
    }

    @Override
    public void push(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        node.next = this.head;
        this.head = node;
        this.size++;
    }

    @Override
    public E pop() {
        if (this.isEmpty()) throw new RuntimeException("The Queue is empty");
        E item = this.head.element;
        this.head = this.head.next;
        this.size--;
        return item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedNode<E> current = LinkedStack.this.head;

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
        Stack<Integer> stack = new LinkedStack<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            stack.push(scanner.nextInt());
        }
        for (int i : stack) {
            System.out.println(i);
        }
    }
}
