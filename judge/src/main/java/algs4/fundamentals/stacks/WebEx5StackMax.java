package algs4.fundamentals.stacks;

import core.stack.MinStack;
import core.stack.Stack;

import java.util.Comparator;
import java.util.Scanner;

/**
 * https://algs4.cs.princeton.edu/13stacks/
 * 5. Stack + max. Create a data structure
 * that efficiently supports the stack operations (pop and push)
 * and also return the maximum element.
 * Assume the elements are integers or reals so that you can compare them.
 * Hint: use two stacks, one to store all of the elements and a second stack to store the maximums.
 */
public class WebEx5StackMax {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            MinStack<Integer> maxStack = new MinStack<>(Stack::fromSinglyLinkedListDoubleReference, Comparator.reverseOrder());
            while (sc.hasNext()) {
                String op = sc.next();
                switch (op) {
                    case "push":
                        maxStack.push(sc.nextInt());
                        break;
                    case "pop":
                        System.out.println(maxStack.pop());
                        break;
                    case "max":
                        System.out.println(maxStack.min());
                        break;
                }
            }
        }
    }
}
