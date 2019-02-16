package code.adt.stack;

import code.adt.list.ArrayList;

import java.util.Iterator;
import java.util.Scanner;

public class ResizingArrayStack<E> implements Stack<E> {
    private final ArrayList<E> list = new ArrayList<>();

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public E peek() {
        return this.list.get(this.list.size() - 1);
    }

    @Override
    public void push(E element) {
        this.list.add(element);
    }

    @Override
    public E pop() {
        return this.list.remove();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = ResizingArrayStack.this.size() - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                return ResizingArrayStack.this.list.get(this.index--);
            }
        };
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (!item.equals("-")) stack.push(item);
            else if (!stack.isEmpty()) System.out.print(stack.pop() + " ");
        }
        System.out.println("(" + stack.size() + " left on stack)");
    }
}
