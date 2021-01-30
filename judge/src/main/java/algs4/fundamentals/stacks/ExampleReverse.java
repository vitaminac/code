package algs4.fundamentals.stacks;

import core.Stack;

import java.util.Scanner;

public class ExampleReverse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Integer> stack = Stack.fromSinglyLinkedListDoubleReference();
        while (scanner.hasNextInt()) {
            stack.push(scanner.nextInt());
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}