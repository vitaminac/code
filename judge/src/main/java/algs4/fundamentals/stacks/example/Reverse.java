package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import collections.deque.LinkedListSteque;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.stack.Stack;

/**
 * https://algs4.cs.princeton.edu/13stacks/Reverse.java.html
 */
public class Reverse {
    public static void main(String[] args) {
        Stack<Integer> stack = Stack.fromSteque(() -> new LinkedListSteque<>(SinglyLinkedListDoubleReference::new));
        while (!StdIn.isEmpty()) {
            stack.push(StdIn.readInt());
        }
        for (int i : stack) {
            StdOut.println(i);
        }
    }
}