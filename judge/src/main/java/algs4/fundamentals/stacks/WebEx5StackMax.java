package algs4.fundamentals.stacks;

import code.adt.MinStack;

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
            MinStack<Integer> maxStack = new MinStack<Integer>(Comparator.reverseOrder());
            while (sc.hasNext()) {
                String op = sc.next();
                if (op.equals("push")) {
                    maxStack.push(sc.nextInt());
                } else if (op.equals("pop")) {
                    System.out.println(maxStack.pop());
                } else if (op.equals("max")) {
                    System.out.println(maxStack.min());
                }
            }
        }
    }
}
