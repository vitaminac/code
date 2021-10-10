package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import core.linkedlist.SinglyLinkedListDoubleReference;

/**
 * https://algs4.cs.princeton.edu/13stacks/Bag.java.html
 */
public class Bag {
    public static void main(String[] args) {
        core.bag.Bag<String> bag = core.bag.Bag.fromSteque(SinglyLinkedListDoubleReference::new);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
    }
}
