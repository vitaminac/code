package algs4.fundamentals.stacks;

import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.stack.Stack;

import java.util.Scanner;

public class WebEx9TuringTape {
    public static void main(String[] args) {
        Stack<Integer> left = Stack.fromSteque(SinglyLinkedListDoubleReference::new);;
        Stack<Integer> right = Stack.fromSteque(SinglyLinkedListDoubleReference::new);;
        int active = 0;

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (token.equals("moveLeft")) {
                right.push(active);
                if (left.isEmpty()) {
                    active = 0;
                } else {
                    active = left.pop();
                }
            } else if (token.equals("moveRight")) {
                left.push(active);
                if (right.isEmpty()) {
                    active = 0;
                } else {
                    active = right.pop();
                }
            } else if (token.equals("look")) {
                System.out.println(active);
            } else if (token.equals("write")) {
                active = scanner.nextInt();
            }
        }
    }
}
