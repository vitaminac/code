package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import core.stack.Stack;

/**
 * https://algs4.cs.princeton.edu/13stacks/Reverse.java.html
 */
public class Reverse {
    public static void main(String[] args) {
        Stack<Integer> stack = Stack.fromSinglyLinkedListDoubleReference();
        while (!StdIn.isEmpty()) {
            stack.push(StdIn.readInt());
        }
        while (!stack.isEmpty()) {
            StdOut.println(stack.pop());
        }
    }
}