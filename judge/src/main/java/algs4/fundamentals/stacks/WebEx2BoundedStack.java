package algs4.fundamentals.stacks;

import core.BoundedStack;
import core.Stack;

import java.util.Scanner;

public class WebEx2BoundedStack {
    public static void main(String[] args) {
        Stack<String> stack = new BoundedStack<>(5);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            stack.push(scanner.next());
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}