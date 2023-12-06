package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdOut;
import collections.deque.ArrayDeque;

/**
 * https://algs4.cs.princeton.edu/13stacks/ResizingArrayBag.java.html
 */
public class ResizingArrayBag {
    public static void main(String[] args) {
        collections.bag.Bag<String> bag = collections.bag.Bag.fromDeque(ArrayDeque::new);
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
