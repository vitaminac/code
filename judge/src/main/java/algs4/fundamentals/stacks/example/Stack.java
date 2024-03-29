package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import collections.deque.LinkedListSteque;
import collections.linkedlist.SinglyLinkedListDoubleReference;

/**
 * https://algs4.cs.princeton.edu/13stacks/Stack.java.html
 */
public class Stack {
    public static void main(String[] args) {
        collections.stack.Stack<String> stack = collections.stack.Stack.fromSteque(() -> new LinkedListSteque<>(SinglyLinkedListDoubleReference::new));
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                StdOut.print(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
