package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import core.list.ArrayList;
import core.stack.Stack;

/**
 * https://algs4.cs.princeton.edu/13stacks/ResizingArrayStack.java.html
 */
public class ResizingArrayStack {
    public static void main(String[] args) {
        Stack<String> stack = Stack.fromList(ArrayList::new);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (!stack.isEmpty()) StdOut.print(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
