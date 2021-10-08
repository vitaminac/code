package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import core.stack.BoundedStack;

/**
 * https://algs4.cs.princeton.edu/13stacks/FixedCapacityStackOfStrings.java.html
 */
public class FixedCapacityStackOfStrings extends BoundedStack<String> {
    public FixedCapacityStackOfStrings(int capacity) {
        super(capacity);
    }

    public static void main(String[] args) {
        int max = StdIn.readInt();
        final var stack = new FixedCapacityStackOfStrings(max);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (stack.isEmpty()) StdOut.println("BAD INPUT");
            else StdOut.print(stack.pop() + " ");
        }
        StdOut.println();

        // print what's left on the stack
        StdOut.print("Left on stack: ");
        for (String s : stack) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}

