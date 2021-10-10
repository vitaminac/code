package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdOut;
import core.deque.ArrayDeque;

/**
 * https://algs4.cs.princeton.edu/13stacks/ResizingArrayBag.java.html
 */
public class ResizingArrayBag {
    public static void main(String[] args) {
        core.bag.Bag<String> bag = core.bag.Bag.fromDeque(ArrayDeque::new);
        bag.add("Hello");
        bag.add("World");
        bag.add("how");
        bag.add("are");
        bag.add("you");

        for (String s : bag) {
            StdOut.println(s);
        }
    }
}
